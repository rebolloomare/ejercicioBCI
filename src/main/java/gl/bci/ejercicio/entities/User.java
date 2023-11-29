package gl.bci.ejercicio.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
public class User {

    @Id
    private String id;

    private String name;

    private String email;

    private String password;

    private String token;

    private LocalDate created;

    private LocalDate lastLogin;

    private Boolean active;

    @OneToMany
    private List<Phone> phones;

    public User() {

    }
}
