package gl.bci.ejercicio.controller;

import gl.bci.ejercicio.model.UserModel;
import gl.bci.ejercicio.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserModel> signUp(@Valid @RequestBody UserModel user){
        UserModel savedUser = userService.signUp(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getUuid()).toUri();
        return ResponseEntity.created(location).build();
    }
}
