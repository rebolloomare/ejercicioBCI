package gl.bci.ejercicio.service;

import gl.bci.ejercicio.exception.UserAlreadyExistException;
import gl.bci.ejercicio.model.UserDto;
import gl.bci.ejercicio.model.response.UserResponse;

public interface UserService {

    UserResponse signUp(UserDto userDto) throws UserAlreadyExistException;
}
