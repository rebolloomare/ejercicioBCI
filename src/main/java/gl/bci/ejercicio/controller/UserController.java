package gl.bci.ejercicio.controller;

import gl.bci.ejercicio.auth.JwtUtil;
import gl.bci.ejercicio.entities.User;
import gl.bci.ejercicio.exception.UserAlreadyExistException;
import gl.bci.ejercicio.model.dto.UserDto;
import gl.bci.ejercicio.model.response.ErrorDetails;
import gl.bci.ejercicio.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Arrays;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    @PostMapping("/sign-up")
    public ResponseEntity<Object> signUp(@RequestBody @Valid UserDto userDto) {
        UserDto userResponse;
        try {
            User user = userService.signUp(userDto);
            if(user == null){
                ErrorDetails errorResponse = new ErrorDetails(LocalDateTime.now(),
                        HttpStatus.NO_CONTENT.value(),
                        "Error al crear al nuevo Usuario: " + userDto.getEmail());
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(errorResponse);
            }
            userResponse = modelMapper.map(user, UserDto.class);
            return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
        } catch (UserAlreadyExistException e) {
            ErrorDetails errorResponse = new ErrorDetails(LocalDateTime.now(),
                    HttpStatus.CONFLICT.value(), "El Usuario ya existe: " + userDto.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        } catch (Exception e) {
            ErrorDetails errorResponse = new ErrorDetails(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(), Arrays.toString(e.getStackTrace()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping("/auth/login")
    public ResponseEntity<Object> login(@Valid @RequestBody UserDto userDto){
        UserDto userResponse;
        try {

            String token = JwtUtil.getBearerTokenHeader();
            userDto.setToken(token);

            User user = userService.login(userDto);
            user.setLastLogin(LocalDateTime.now());
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
            userResponse = modelMapper.map(user, UserDto.class);

            if(userResponse == null){
                ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        "Error al consultar al usuario");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
            }
            return ResponseEntity.ok(userResponse);
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