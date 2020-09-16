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
    public List<Sub> flight_subs;
    private Float flight_score;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Sub {
        public Integer sub_num;
        public String sub_val;
    }
}
