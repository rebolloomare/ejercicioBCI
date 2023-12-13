package gl.bci.ejercicio.service;

import gl.bci.ejercicio.entities.User;
import gl.bci.ejercicio.model.dto.UserDto;
import gl.bci.ejercicio.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Test
    void test_Login() {
        User user = new User();
        user.setEmail("omar.rebollo@gmail.com");
        user.setPassword("a2asfGfdfdf4");

        UserDto userDto = new UserDto();
        userDto.setEmail("omar.rebollo@gmail.com");
        userDto.setPassword("a2asfGfdfdf4");

        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(user);
        assertEquals(user, userService.login(userDto));
    }

    @Test
    void test_Login_User_Not_Found() {
        UserDto userDto = new UserDto();
        userDto.setEmail("xxxx@gmail.com");
        userDto.setPassword("a2asfGfdfdf4");

        assertNull(userService.login(userDto));
    }

}