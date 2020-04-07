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
public class VaultFlightDTO {
    private Integer round_number;
    private Integer pilot_id;
    private String group;
    private Integer order;
    private String minutes;
    private String seconds;
    private Integer penalty;
    private Sub subs;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Sub {
        String sub1;
        String sub2;
        String sub3;
        String sub4;
        String sub5;
        String sub6;
        String sub7;
        String sub8;
        String sub9;
        String sub10;
        String sub11;
    }
}
