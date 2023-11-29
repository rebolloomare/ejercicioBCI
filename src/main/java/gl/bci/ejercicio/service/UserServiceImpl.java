package gl.bci.ejercicio.service;

import gl.bci.ejercicio.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    /**
     * @param user
     * @return
     */
    @Override
    public User signUp(User user) {

        return user;
    }
}
