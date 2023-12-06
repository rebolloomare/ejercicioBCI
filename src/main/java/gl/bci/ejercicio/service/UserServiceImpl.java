package gl.bci.ejercicio.service;

import gl.bci.ejercicio.entities.Phone;
import gl.bci.ejercicio.entities.User;
import gl.bci.ejercicio.exception.UserAlreadyExistException;
import gl.bci.ejercicio.model.dto.UserDto;
import gl.bci.ejercicio.model.request.LoginRequest;
import gl.bci.ejercicio.model.dto.PhoneDto;
import gl.bci.ejercicio.model.response.LoginResponse;
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
     * @param loginRequest
     * @return
     */
    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setId(user.getId());
        loginResponse.setCreated(user.getCreated());
        loginResponse.setLastLogin(LocalDateTime.now());
        loginResponse.setToken(user.getToken());
        loginResponse.setActive(user.getActive());
        loginResponse.setName(user.getName());
        loginResponse.setEmail(user.getEmail());
        loginResponse.setPassword(user.getPassword());
        loginResponse.setPhones(user.getPhones());

        return loginResponse;
    }

}
