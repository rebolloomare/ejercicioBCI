package gl.bci.ejercicio.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ErrorDetails {

    private LocalDate timestamp;

    private Integer codigo;

    private String detail;
}
