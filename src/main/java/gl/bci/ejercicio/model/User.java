package gl.bci.ejercicio.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue
    private String id;

    private String name;

    @Email(message = "Formato de correo incorrecto")
    private String email;

    @Pattern(regexp = "^[A-Za-z0-9]*$", message = "Password incorrecto")
    private String password;

    @OneToMany
    private List<Phone> phones;

    public User() {

    }
}
