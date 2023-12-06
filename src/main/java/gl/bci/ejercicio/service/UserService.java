package gl.bci.ejercicio.service;

import gl.bci.ejercicio.entities.User;
import gl.bci.ejercicio.exception.UserAlreadyExistException;
import gl.bci.ejercicio.model.dto.UserDto;
import gl.bci.ejercicio.model.request.LoginRequest;
import gl.bci.ejercicio.model.response.LoginResponse;

public interface UserService {

    User signUp(UserDto userDto) throws UserAlreadyExistException;

    LoginResponse login(LoginRequest loginRequest);
}
