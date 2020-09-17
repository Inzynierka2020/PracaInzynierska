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
public class VaultPrelimStandingsDTO {
    private Integer total_rounds;
    private Integer total_drops;
    private List<VaultStandingDTO> standings;
}
