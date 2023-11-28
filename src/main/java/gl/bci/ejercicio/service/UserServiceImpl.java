package gl.bci.ejercicio.service;

import gl.bci.ejercicio.model.UserModel;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    /**
     * @param user
     * @return
     */
    @Override
    public UserModel signUp(UserModel user) {

        return user;
    }
}
