package gl.bci.ejercicio.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@Entity
public class Phone {

    @Id
    @GeneratedValue
    private Long id;

    private Long number;

    private Integer cityCode;

    private String countryCode;

    public Phone() {

    }
}
