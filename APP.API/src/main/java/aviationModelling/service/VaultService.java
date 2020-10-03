package aviationModelling.service;

import aviationModelling.dto.FlightDTO;
import aviationModelling.dto.RoundDTO;
import aviationModelling.dto.VaultEventDataDTO;
import aviationModelling.dto.VaultResponseDTO;

public interface VaultService {

    VaultEventDataDTO getEventInfoFull(int eventId);
    VaultResponseDTO postScore(FlightDTO flightDTO);
    VaultResponseDTO  updateEventRoundStatus(Integer roundNum, Integer eventId);
}
