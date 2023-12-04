package gl.bci.ejercicio.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class UserRequest {

    private String id;

    private String name;

    @Email(message = "Formato de correo incorrecto")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d.*\\d)(?=.*[a-z]).{8,12}$", message = "Password incorrecto")
    private String password;

    private String token;

    private String role;

    private LocalDateTime created;

    private LocalDateTime lastLogin;

    private Boolean active;

    private List<PhoneRequest> phones;

    public UserRequest(String email, String password){
        this.email = email;
        this.password = password;
    }

}
