package aviationModelling.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class VaultPilotDTO {
    private Integer pilot_id;
    private Integer pilot_bib;
    private String pilot_first_name;
    private String pilot_last_name;
    private String country_code;

}
