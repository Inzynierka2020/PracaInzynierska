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
public class VaultStandingDTO {
    private Integer pilot_id;
    private Integer pilot_position;
    private String pilot_first_name;
    private String pilot_last_name;
    private Float total_score;
    private Float total_percent;
    private Float total_diff;
    private Float total_drop;
    private Float total_penalties;
    private List<VaultRoundsDTO> rounds;
}
