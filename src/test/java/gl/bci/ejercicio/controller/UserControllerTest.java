package gl.bci.ejercicio.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import gl.bci.ejercicio.entities.User;
import gl.bci.ejercicio.exception.UserAlreadyExistException;
import gl.bci.ejercicio.model.dto.UserDto;
import gl.bci.ejercicio.service.UserServiceImpl;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    UserServiceImpl userService;

    ObjectMapper objectMapper = new ObjectMapper();

    User user;

    User userNull;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(UUID.randomUUID().toString())
                .name("omare")
                .password("a2asfGfdfdf4")
                .email("omar.rebollo@gmail.com")
                .active(Boolean.TRUE)
                .token("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvbWFyLnJlYm9sbG9AeWFob28uY29tIiwibmFtZSI6Im9tYXIiLCJlbWFpbCI6Im9tYXIucmVib2xsb0B5YWhvby5jb20iLCJleHAiOjE5MTc3MDg3MzV9.YHNmQQZMV7Kf_-b8lpsOKJqhbmdl4n5a_wl5kEJqtCg")
                .build();
        userNull = null;
    }

    @Test
    void test_SignUp_Created() throws UserAlreadyExistException, JsonProcessingException, JSONException {
        when(userService.signUp(any(UserDto.class))).thenReturn(user);

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        String expected = objectMapper.writeValueAsString(user);

        ResponseEntity<String> response = restTemplate
                .postForEntity("/sign-up", user, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);

        verify(userService, times(1)).signUp(any(UserDto.class));

    }

    @Test
    void test_SignUp_Empty_Email_User() throws UserAlreadyExistException, JSONException {
        String emptyUser = "{\"name\":\"ABC\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(emptyUser, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("/sign-up",
                entity, String.class);

        String expectedResponse = "{\"codigo\":400,\"detail\":\"[Email no puede ir vacio]\"}";
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        JSONAssert.assertEquals(expectedResponse, response.getBody(), false);

        verify(userService, times(0)).signUp(any(UserDto.class));

    }

    @Test
    void test_Login_Forbidden() throws Exception {
        String expected = "{\"status\":403,\"error\":\"Forbidden\",\"path\":\"/bci/users/auth/login\"}";

        ResponseEntity<String> response = restTemplate
                .postForEntity("/auth/login", user, String.class);

        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatusCode().value());

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    void test_Login_OK() throws JSONException {
        when(userService.login(any(UserDto.class))).thenReturn(user);

        String token = "Bearer " + user.getToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);

        JSONObject userJsonObject = new JSONObject();
        userJsonObject.put("email", user.getEmail());
        userJsonObject.put("password", user.getPassword());

        HttpEntity<String> request = new HttpEntity<>(userJsonObject.toString(), headers);

        ResponseEntity<String> response = restTemplate
                .postForEntity("/auth/login", request, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(userService, times(1)).login(any(UserDto.class));

    }

    @Test
    void test_Login_BadRequest() throws JSONException {
        when(userService.login(any(UserDto.class))).thenReturn(user);

        String token = "Bearer " + user.getToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);

        JSONObject userJsonObject = new JSONObject();
        userJsonObject.put("email", user.getId());
        userJsonObject.put("password", user.getPassword());

        HttpEntity<String> request = new HttpEntity<>(userJsonObject.toString(), headers);

        ResponseEntity<String> response = restTemplate
                .postForEntity("/auth/login", request, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    void test_Login_Wrong_Password() throws JSONException {
        when(userService.login(any(UserDto.class))).thenReturn(user);

        String token = "Bearer " + user.getToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);

        JSONObject userJsonObject = new JSONObject();
        userJsonObject.put("email", user.getEmail());
        userJsonObject.put("password", "erjfkldsdjfklj");

        HttpEntity<String> request = new HttpEntity<>(userJsonObject.toString(), headers);

        ResponseEntity<String> response = restTemplate
                .postForEntity("/auth/login", request, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
