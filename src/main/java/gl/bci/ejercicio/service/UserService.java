package gl.bci.ejercicio.service;

import gl.bci.ejercicio.model.UserDto;
import gl.bci.ejercicio.exception.UserAlreadyExistException;
import gl.bci.ejercicio.entities.User;

public interface UserService {

    User signUp(UserDto userDto) throws UserAlreadyExistException;
}
