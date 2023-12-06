package gl.bci.ejercicio.service;

import gl.bci.ejercicio.entities.Phone;
import gl.bci.ejercicio.entities.User;
import gl.bci.ejercicio.exception.UserAlreadyExistException;
import gl.bci.ejercicio.model.dto.PhoneDto;
import gl.bci.ejercicio.model.dto.UserDto;
import gl.bci.ejercicio.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * @param userRequest
     * @return
     */
    @Override
    public User signUp(UserDto userRequest) throws UserAlreadyExistException {
        if(userRepository.findByEmail(userRequest.getEmail()) != null){
            throw new UserAlreadyExistException("El Usuario ya existe: " + userRequest.getEmail());
        }
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setLastLogin(LocalDateTime.now());
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());

        List<PhoneDto> phonesDtoList = userRequest.getPhones();
        List<Phone> phonesEntityList = new ArrayList<>();
        Phone phone = null;
        for(PhoneDto phoneDto : phonesDtoList){
            phone = new Phone();
            phone.setNumber(phoneDto.getNumber());
            phone.setCityCode(phoneDto.getCityCode());
            phone.setCountryCode(phoneDto.getCountryCode());
            phonesEntityList.add(phone);
        }
        user.setPhones(phonesEntityList);

        user.setToken(userRequest.getToken());
        user.setCreated(LocalDateTime.now());
        user.setActive(Boolean.TRUE);

        return userRepository.save(user);
    }

    /**
     * @param userDto
     * @return
     */
    @Override
    public User login(UserDto userDto) {
        return userRepository.findByEmail(userDto.getEmail());
    }

}
