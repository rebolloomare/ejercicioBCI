package gl.bci.ejercicio.service;

import gl.bci.ejercicio.entities.User;
import gl.bci.ejercicio.exception.UserAlreadyExistException;
import gl.bci.ejercicio.model.dto.UserDto;

public interface UserService {

    User signUp(UserDto userDto) throws UserAlreadyExistException;

    User login(UserDto userDto);
}
