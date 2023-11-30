package gl.bci.ejercicio.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserResponse {

    private String id;

    private LocalDateTime created;

    private LocalDateTime lastLogin;

    private String token;

    private Boolean active;

}
