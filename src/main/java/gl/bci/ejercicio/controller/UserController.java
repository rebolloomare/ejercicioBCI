package gl.bci.ejercicio.controller;

import gl.bci.ejercicio.exception.UserAlreadyExistException;
import gl.bci.ejercicio.model.UserDto;
import gl.bci.ejercicio.model.request.LoginRequest;
import gl.bci.ejercicio.model.response.ErrorDetails;
import gl.bci.ejercicio.model.response.LoginResponse;
import gl.bci.ejercicio.model.response.UserResponse;
import gl.bci.ejercicio.service.UserService;
import gl.bci.ejercicio.util.JwtTokensUtility;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
public class UserController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final JwtTokensUtility jwtTokensUtility;

    public UserController(UserService userService,
                          AuthenticationManager authenticationManager,
                          JwtTokensUtility jwtTokensUtility) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokensUtility = jwtTokensUtility;
    }

    @PostMapping("/users/sign-up")
    public ResponseEntity<Object> signUp(@Valid @RequestBody UserDto userDto) {
        UserResponse userResponse;
        try {
            userResponse = userService.signUp(userDto);
        } catch (UserAlreadyExistException e) {
            ErrorDetails errorResponse = new ErrorDetails(LocalDate.now(),
                    HttpStatus.CONFLICT.value(), "El Usuario ya existe " + userDto.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }

        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PostMapping("/users/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest loginRequest){
        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest
                            .getEmail(), loginRequest.getPassword()));
            String email = authentication.getName();
            UserDto userDto = new UserDto(email,"");
            String token = jwtTokensUtility.createToken(userDto);
            LoginResponse loginRes = new LoginResponse(email, token);

            return ResponseEntity.ok(loginRes);

        } catch (BadCredentialsException e) {
            ErrorDetails errorResponse = new ErrorDetails(LocalDate.now(),
                    HttpStatus.BAD_REQUEST.value(), "Email o password no válidos");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            ErrorDetails errorResponse = new ErrorDetails(LocalDate.now(),
                    HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

}
