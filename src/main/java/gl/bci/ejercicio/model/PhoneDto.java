package gl.bci.ejercicio.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PhoneDto {

    private Long number;

    private Integer cityCode;

    private String countryCode;

}
