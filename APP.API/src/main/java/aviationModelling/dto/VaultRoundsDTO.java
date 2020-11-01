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
public class VaultRoundsDTO {
    private Integer round_number;
    private Float round_score;
    private Float round_score_status;
    private List<VaultFlightDTO> flights;
}
