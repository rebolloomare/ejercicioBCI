package gl.bci.ejercicio.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorDetails {

    private LocalDateTime timestamp;

    private Integer codigo;

    private String detail;
}
