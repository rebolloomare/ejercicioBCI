package gl.bci.ejercicio.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDto {

    private String name;

    @Email(message = "Formato de correo incorrecto")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d.*\\d)(?=.*[a-z]).{8,12}$", message = "Password incorrecto")
    private String password;

    private String token;

    private LocalDate created;

    private LocalDate lastLogin;

    private Boolean active;

    private List<PhoneDto> phones;

    public UserDto(String email, String password){
        this.email = email;
        this.password = password;
    }

}
