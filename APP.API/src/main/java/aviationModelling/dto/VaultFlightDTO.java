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
    private String flight_group;
    private Integer flight_order;
    private String flight_seconds;
    private Integer flight_penalty;
    private Float flight_score;
    private Integer flight_dropped; //1-true, 0-false
    private Integer score_status; //1-valid, 0-invalid
    public List<Sub> flight_subs;


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Sub {
        public Integer sub_num;
        public String sub_val;
    }
}
