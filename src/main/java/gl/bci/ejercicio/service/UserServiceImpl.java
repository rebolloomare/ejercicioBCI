package gl.bci.ejercicio.service;

import gl.bci.ejercicio.entities.User;
import gl.bci.ejercicio.exception.UserAlreadyExistException;
import gl.bci.ejercicio.model.UserDto;
import gl.bci.ejercicio.model.response.UserResponse;
import gl.bci.ejercicio.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * @param userDto
     * @return
     */
    @Override
    public UserResponse signUp(UserDto userDto) throws UserAlreadyExistException {
        if(userRepository.findByEmail(userDto.getEmail()) != null){
            throw new UserAlreadyExistException("El Usuario ya existe: " + userDto.getEmail());
        }
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPhones(null);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setToken(userDto.getToken());
        user.setCreated(LocalDateTime.now());
        user.setLastLogin(null);
        user.setActive(Boolean.TRUE);

        User savedUser = userRepository.save(user);
        UserResponse userResponse = new UserResponse();

        userResponse.setId(savedUser.getId());
        userResponse.setCreated(savedUser.getCreated());
        userResponse.setLastLogin(savedUser.getLastLogin());
        userResponse.setToken(savedUser.getToken());
        userResponse.setActive(savedUser.getActive());

        return userResponse;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(roles.toArray(new String[0]))
                .build();
    }
}
