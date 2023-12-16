package gl.bci.ejercicio.service;

import gl.bci.ejercicio.auth.JwtUtil;
import gl.bci.ejercicio.entities.User;
import gl.bci.ejercicio.exception.UserAlreadyExistException;
import gl.bci.ejercicio.model.dto.UserDto;
import gl.bci.ejercicio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JwtUtil jwtUtil;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    /**
     * @param userDto
     * @return
     */
    @Override
    public User signUp(UserDto userDto) throws UserAlreadyExistException {
        User user;

        if(userRepository.findByEmail(userDto.getEmail()) != null){
            throw new UserAlreadyExistException("El Usuario ya existe: " + userDto.getEmail());
        }

        String passwordEncrypted = bCryptPasswordEncoder.encode(userDto.getPassword());
        userDto.setPassword(passwordEncrypted);
        String token = jwtUtil.createToken(userDto);
        userDto.setToken(token);
        userDto.setRole("USER");
        userDto.setId(UUID.randomUUID().toString());
        userDto.setCreated(LocalDateTime.now());
        userDto.setActive(Boolean.TRUE);

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        user = modelMapper.map(userDto, User.class);

        return userRepository.save(user);
    }

    /**
     * @return
     */
    @Override
    public User login(UserDto userDto) {
        return userRepository.findByEmail(userDto.getEmail());
    }

}
