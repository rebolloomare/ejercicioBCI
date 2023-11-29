package gl.bci.ejercicio.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class LoginRequest {

    @Email(message = "Formato de correo incorrecto")
    private  String email;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d.*\\d)(?=.*[a-z]).{8,12}$", message = "Password incorrecto")
    private String password;

}
