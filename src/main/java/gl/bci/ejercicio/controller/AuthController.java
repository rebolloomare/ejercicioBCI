package gl.bci.ejercicio.controller;

import gl.bci.ejercicio.auth.JwtUtil;
import gl.bci.ejercicio.model.request.LoginRequest;
import gl.bci.ejercicio.model.request.UserRequest;
import gl.bci.ejercicio.model.response.ErrorDetails;
import gl.bci.ejercicio.model.response.LoginResponse;
import gl.bci.ejercicio.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final JwtUtil jwtUtil;

    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager,
                          BCryptPasswordEncoder bCryptPasswordEncoder,
                          JwtUtil jwtUtil,
                          UserService userService){
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest loginRequest){
        try {
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                                    loginRequest.getPassword()));

            String email = authentication.getName();
            UserRequest user = new UserRequest(email, "");
            String token = jwtUtil.createToken(user);

            LoginResponse loginResponse = userService.login(loginRequest);
            if(loginResponse == null){
                ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        "Error al consultar al usuario");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
            }
            return ResponseEntity.ok(loginResponse);
        } catch (BadCredentialsException e){
            ErrorDetails errorResponse = new ErrorDetails(LocalDateTime.now(),
                    HttpStatus.BAD_REQUEST.value(),"Usuario y/o password incorrectos");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            ErrorDetails errorResponse = new ErrorDetails(LocalDateTime.now(),
                    HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

}
