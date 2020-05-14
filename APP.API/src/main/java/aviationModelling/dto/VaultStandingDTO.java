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
    private Float total_score;
    private Integer total_penalties;
    private Float total_percent;
    private List<VaultRoundDTO> rounds;
}
