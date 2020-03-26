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
    private class Sub {
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
    }
}
