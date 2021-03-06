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
    private Integer event_id;
    private String pilot_first_name;
    private String pilot_last_name;
    private String pilot_class;
    private String pilot_ama;
    private String pilot_fai;
    private String pilot_fai_license;
    private String pilot_team;
//    private List<VaultFlightDTO> flights;
}
