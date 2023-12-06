package gl.bci.ejercicio.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String id;

    private LocalDateTime created;

    private LocalDateTime lastLogin;

    private String token;

    private Boolean active;

    private String name;

    @NotEmpty(message = "Email no puede ir vacio")
    @Email(message = "Formato de correo no valido")
    //@Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d.*\\d)(?=.*[a-z]).{8,12}$", message = "Password incorrecto")
    private String password;

    private List<PhoneDto> phones;

    @JsonIgnore
    private String role;

}
