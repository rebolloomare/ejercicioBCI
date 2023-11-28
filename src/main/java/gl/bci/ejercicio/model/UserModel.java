package gl.bci.ejercicio.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@AllArgsConstructor
public class UserModel {

    private String uuid;

    private String name;

    @Email(message = "Formato de correo incorrecto")
    private String email;

    @Pattern(regexp = "^[A-Za-z0-9]*$", message = "Password incorrecto")
    private String password;

    private List<PhoneModel> phones;

}
