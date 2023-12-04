package gl.bci.ejercicio.service;

import gl.bci.ejercicio.exception.UserAlreadyExistException;
import gl.bci.ejercicio.model.request.LoginRequest;
import gl.bci.ejercicio.model.request.UserRequest;
import gl.bci.ejercicio.model.response.LoginResponse;
import gl.bci.ejercicio.model.response.UserResponse;

public interface UserService {

    UserResponse signUp(UserRequest userRequest) throws UserAlreadyExistException;

    LoginResponse login(LoginRequest loginRequest);
}
