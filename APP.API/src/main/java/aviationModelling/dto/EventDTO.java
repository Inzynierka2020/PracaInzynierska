package aviationModelling.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
    private Integer event_id;
    private String event_name;
    private String location;
    private String start_date;
    private String end_date;
    private String event_type;
    private Integer rounds;
    private List<Task> tasks;
    private List<PilotDTO> pilots;

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    class Task {
        private Integer round_number;
        private String task_code;
    }
}
