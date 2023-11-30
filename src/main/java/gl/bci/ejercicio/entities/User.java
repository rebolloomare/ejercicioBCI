package gl.bci.ejercicio.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
public class User {

    @Id
    private String id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String token;

    @Column
    private LocalDateTime created;

    @Column
    private LocalDateTime lastLogin;

    @Column
    private Boolean active;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Phone> phones;

    public User() {

    }
}
