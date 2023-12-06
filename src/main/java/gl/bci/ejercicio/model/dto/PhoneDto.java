package gl.bci.ejercicio.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneDto {

    private Long number;

    @JsonProperty("citycode")
    private Integer cityCode;

    @JsonProperty("countrycode")
    private String countryCode;

}
