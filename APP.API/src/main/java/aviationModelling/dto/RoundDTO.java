package aviationModelling.dto;


import aviationModelling.entity.Flight;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoundDTO {
    private Integer roundNum;
    private Integer eventId;
    private boolean isCancelled;
    private boolean isFinished;
    //@JsonIgnore
    private List<FlightDTO> flights;
}
