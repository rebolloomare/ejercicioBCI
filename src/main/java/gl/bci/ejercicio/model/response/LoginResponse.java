package gl.bci.ejercicio.model.response;

import gl.bci.ejercicio.entities.Phone;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class LoginResponse {

    private String id;

    private String name;

    private String email;

    private String password;

    private String token;

    private LocalDateTime created;

    private LocalDateTime lastLogin;

    private Boolean active;

    private List<Phone> phones;

    public LoginResponse(String email, String token){
        this.email = email;
        this.password = token;
    }
}
