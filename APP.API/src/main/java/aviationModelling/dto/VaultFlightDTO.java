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
    private String seconds;
    private Integer penalty;
    public List<Sub> subs;
    private Float score;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Sub {
        public Integer sub_num;
        public String sub_val;
    }
}
