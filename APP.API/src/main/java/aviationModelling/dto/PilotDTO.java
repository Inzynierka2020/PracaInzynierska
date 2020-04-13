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
public class PilotDTO {
    private Integer eventPilotId;
    private Integer pilotId;
    private Integer eventId;
    private String pilotBib;
    private String firstName;
    private String lastName;
    private String pilotClass;
    private String ama;
    private String fai;
    private String faiLicence;
    private String teamName;
    private Float discarded1;
    private Float discarded2;
    private Float score;
    @JsonView(Views.Internal.class)
    private List<FlightDTO> flights;
}

