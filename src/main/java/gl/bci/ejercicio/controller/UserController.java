package gl.bci.ejercicio.controller;

import gl.bci.ejercicio.auth.JwtUtil;
import gl.bci.ejercicio.entities.User;
import gl.bci.ejercicio.exception.UserAlreadyExistException;
import gl.bci.ejercicio.model.dto.UserDto;
import gl.bci.ejercicio.model.response.ErrorDetails;
import gl.bci.ejercicio.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
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
public class UserController {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final JwtUtil jwtUtil;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ModelMapper modelMapper;

    public UserController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          UserService userService,
                          BCryptPasswordEncoder bCryptPasswordEncoder,
                          ModelMapper modelMapper) {
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Object> signUp(@RequestBody @Valid UserDto userDto) {
        UserDto userResponse;
        try {
            String passwordEncrypted = bCryptPasswordEncoder.encode(userDto.getPassword());
            String token = jwtUtil.createToken(userDto);
            userDto.setToken(token);
            userDto.setPassword(passwordEncrypted);
            userDto.setRole("USER");

            User user = userService.signUp(userDto);

            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
            userResponse = modelMapper.map(user, UserDto.class);

            if(userResponse == null){
                ErrorDetails errorResponse = new ErrorDetails(LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        "Error al crear al nuevo Usuario");
            }

            return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
        } catch (UserAlreadyExistException e) {
            ErrorDetails errorResponse = new ErrorDetails(LocalDateTime.now(),
                    HttpStatus.CONFLICT.value(), "El Usuario ya existe: " + userDto.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        } catch (Exception e) {
        ErrorDetails errorResponse = new ErrorDetails(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(), "Formato de correo incorrecto");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    }

    @PostMapping("/auth/login")
    public ResponseEntity<Object> login(@Valid @RequestBody UserDto userDto){
        UserDto userResponse;
        try {
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(userDto.getEmail(),
                                    userDto.getPassword()));

            String token = jwtUtil.createToken(userDto);
            userDto.setToken(token);

            User user = userService.login(userDto);
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