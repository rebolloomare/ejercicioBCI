package gl.bci.ejercicio.repository;

import gl.bci.ejercicio.entities.Phone;
import gl.bci.ejercicio.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(UUID.randomUUID().toString())
                .name("omare")
                .password("a2asfGfdfdf4")
                .email("omar.rebollo@gmail.com")
                .active(Boolean.TRUE)
                .created(LocalDateTime.now())
                .lastLogin(null)
                .phones(List.of(new Phone("57574483", "55", "52")))
                .token("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvbWFyLnJlYm9sbG9AeWFob28uY29tIiwibmFtZSI6Im9tYXIiLCJlbWFpbCI6Im9tYXIucmVib2xsb0B5YWhvby5jb20iLCJleHAiOjE5MTc3MDg3MzV9.YHNmQQZMV7Kf_-b8lpsOKJqhbmdl4n5a_wl5kEJqtCg")
                .build();
    }

    @Test
    @DisplayName("JUnit Test case to get User by email")
    void findByEmail() {
        User savedUser = userRepository.save(user);
        User getUser = userRepository.findByEmail(user.getEmail());
        assertEquals(getUser.getEmail(), savedUser.getEmail());
    }

    @Test
    @DisplayName("JUnit Test case to test null as parameter")
    void findByEmailNull() {
        assertNull(userRepository.findByEmail(null));
    }
}