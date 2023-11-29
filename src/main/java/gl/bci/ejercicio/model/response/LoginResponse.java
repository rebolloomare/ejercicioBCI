package gl.bci.ejercicio.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class LoginResponse {

    private String name;

    private String email;

    private String password;

    private String token;

    private LocalDate created;

    private LocalDate lastLogin;

    private Boolean active;

    public LoginResponse(String email, String token){
        this.email = email;
        this.password = token;
    }
}
