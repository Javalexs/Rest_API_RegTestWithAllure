package lombok;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LombokCheck {
    private String email;
    @JsonProperty("first_name")
    private String firstName;
    private String token;
    private String job;
    private String name;

}
