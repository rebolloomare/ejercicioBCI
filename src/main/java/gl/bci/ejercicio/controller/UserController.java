package gl.bci.ejercicio.controller;

import gl.bci.ejercicio.auth.JwtUtil;
import gl.bci.ejercicio.exception.UserAlreadyExistException;
import gl.bci.ejercicio.model.request.UserRequest;
import gl.bci.ejercicio.model.response.ErrorDetails;
import gl.bci.ejercicio.model.response.UserResponse;
import gl.bci.ejercicio.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
public class UserController {

    private final UserService userService;

    private final JwtUtil jwtUtil;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(JwtUtil jwtUtil, UserService userService,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Object> signUp(@Valid @RequestBody UserRequest userRequest) {
        UserResponse userResponse;
        try {
            String passwordEncrypted = bCryptPasswordEncoder.encode(userRequest.getPassword());
            String token = jwtUtil.createToken(userRequest);
            userRequest.setToken(token);
            userRequest.setPassword(passwordEncrypted);
            userRequest.setRole("USER");

            userResponse = userService.signUp(userRequest);
            if(userResponse == null){
                ErrorDetails errorResponse = new ErrorDetails(LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        "Error al crear al nuevo Usuario");
            }

            return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
        } catch (UserAlreadyExistException e) {
            ErrorDetails errorResponse = new ErrorDetails(LocalDateTime.now(),
                    HttpStatus.CONFLICT.value(), "El Usuario ya existe: " + userRequest.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        } catch (Exception e){
            ErrorDetails errorResponse = new ErrorDetails(LocalDateTime.now(),
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }



}
