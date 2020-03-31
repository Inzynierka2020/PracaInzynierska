package aviationModelling.dto;

import aviationModelling.entity.Pilot;
import aviationModelling.entity.Round;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
    private Integer eventId;
    private String eventName;
    private String eventLocation;
    private String eventStartDate;
    private String eventEndDate;
    private String eventType;
    private Integer numberOfRounds;
    @JsonIgnore
    private List<PilotDTO> pilots;
    @JsonIgnore
    private List<RoundDTO> rounds;
}
