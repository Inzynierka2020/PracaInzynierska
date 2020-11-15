package aviationModelling.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@Builder
public class BestScoresDto {
    private FlightDTO bestFromEvent;
    private FlightDTO bestFromRound;
    private List<FlightDTO> bestFromGroups;

}
