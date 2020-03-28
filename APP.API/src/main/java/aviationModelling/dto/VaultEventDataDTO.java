package aviationModelling.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

public class VaultEventDataDTO {
    private String response_code;
    private String error_string;
    private VaultEventDTO event;
}
