package aviationModelling.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class VaultEventDTO {
    private Integer event_id;
    private String event_name;
    private String location_name;
    private String country_code;
    private String event_type_name;
    private Integer total_rounds;
    private List<VaultPilotDTO> pilots;
    private VaultPrelimStandingsDTO prelim_standings;


    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    class Task {
        private Integer round_number;
        private String task_code;
    }
}
