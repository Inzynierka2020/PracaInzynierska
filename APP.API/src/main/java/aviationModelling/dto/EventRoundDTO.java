package aviationModelling.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventRoundDTO {
    private Integer roundNum;
    private Integer eventId;
    private boolean isCancelled;
    private boolean isFinished;
}
