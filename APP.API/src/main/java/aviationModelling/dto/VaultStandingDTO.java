package aviationModelling.dto;

import java.util.List;

public class VaultStandingDTO {
    private Integer pilot_id;
    private Float total_score;
    private Float total_penalties;
    private Float total_percent;
    private List<VaultRoundDTO> rounds;
}
