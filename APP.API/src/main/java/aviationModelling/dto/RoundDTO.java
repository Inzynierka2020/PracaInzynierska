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
    private Integer roundNum;

    @JsonView(Views.Internal.class)
    private List<FlightDTO> flights;

    @JsonView(Views.Internal.class)
    private List<EventRoundDTO> eventRounds;
}
