package gl.bci.ejercicio.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PhoneRequest {

    private Long number;

    @JsonProperty("citycode")
    private Integer cityCode;

    @JsonProperty("countrycode")
    private String countryCode;

}
