package aviationModelling.dto;

import aviationModelling.entity.Flight;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonView(Views.Internal.class)
public class FlightDTO {
//    private Integer eventPilotId;
//    private Integer eventRoundId;
    private Integer pilotId;
    private Integer roundNum;
    private Integer eventId;
    private Integer penalty;
    private Integer order;
    private String group;
    private LocalDateTime flightTime;
    private Float windAvg;
    private Float dirAvg;
    private Float seconds;
    private Float sub1;
    private Float sub2;
    private Float sub3;
    private Float sub4;
    private Float sub5;
    private Float sub6;
    private Float sub7;
    private Float sub8;
    private Float sub9;
    private Float sub10;
    private Float sub11;
    private boolean dns;
    private boolean dnf;
    private Float score;

}
