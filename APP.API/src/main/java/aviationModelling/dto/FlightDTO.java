package aviationModelling.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightDTO {
    private Integer round_number;
    private String group;
    private String order;
    private Integer minutes;
    private Float seconds;
    private Integer penalty;
    private Sub subs;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    class Sub {
        Float sub1;
        Float sub2;
        Float sub3;
        Float sub4;
        Float sub5;
        Float sub6;
        Float sub7;
        Float sub8;
        Float sub9;
        Float sub10;
        Float sub11;
    }
}
