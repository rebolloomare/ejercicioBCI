package gl.bci.ejercicio.service;

import gl.bci.ejercicio.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class CustomUserDetailsServiceTest {

    @InjectMocks
    CustomUserDetailsService customUserDetailsService;

    @Mock
    UserRepository userRepository;

    @Test
    void loadUserByUsername() {
        String email = "omar.rebollo@gmail.com";

    }
}