package gl.bci.ejercicio.controller;

import gl.bci.ejercicio.entities.User;
import gl.bci.ejercicio.exception.UserAlreadyExistException;
import gl.bci.ejercicio.model.dto.UserDto;
import gl.bci.ejercicio.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    MockMvc mockMvc;

    UserDto userDto;

    User userEntity;

    @BeforeEach
    void setUp() {
        userDto = UserDto.builder()
                .email("omar.rebollo@gmail.com")
                .password("a2asfGfdfdf4")
                .build();
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }



    @Test
    void testSignUp() throws Exception, UserAlreadyExistException {

    }

}