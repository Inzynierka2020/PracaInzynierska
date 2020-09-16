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
    private String start_date;
    private String end_date;
    private String event_type_name;
    private Integer total_rounds;
    private List<Task> tasks;
    private List<VaultPilotDTO> pilots;
    private VaultPrelimStandingsDTO prelim_standings;


    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    class Task {
        public Integer round_number;
        public String task_code;
    }
}
