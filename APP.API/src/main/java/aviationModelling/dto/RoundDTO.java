package aviationModelling.dto;


import aviationModelling.entity.Flight;
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
    private boolean isCancelled;
    private boolean isFinished;
    private List<Flight> flights;
}
