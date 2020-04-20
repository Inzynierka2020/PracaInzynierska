package aviationModelling.dto;


import aviationModelling.entity.Flight;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonView(Views.Public.class)
public class RoundDTO {
//    private Integer eventRoundId;
    private Integer roundNum;
    private Integer eventId;
    private Integer numberOfGroups;
    private boolean isCancelled;
    private boolean isFinished;

    @JsonView(Views.Internal.class)
    private List<FlightDTO> flights;
}
