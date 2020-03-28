package aviationModelling.dto;

import aviationModelling.entity.Pilot;
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
    private List<Pilot> pilots;
}
