package aviationModelling.service;

import aviationModelling.dto.FlightDTO;
import aviationModelling.dto.RoundDTO;
import aviationModelling.dto.VaultEventDataDTO;
import aviationModelling.dto.VaultResponseDTO;
import aviationModelling.entity.EventRound;
import aviationModelling.repository.RoundRepository;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class VaultServiceImpl implements VaultService {

    private UrlWizard urlWizard;
    private RestTemplate restTemplate;
    private RoundRepository roundRepository;

    public VaultServiceImpl(UrlWizard urlWizard, RoundRepository roundRepository) {
        this.urlWizard = urlWizard;
        this.restTemplate = new RestTemplate();
        this.roundRepository = roundRepository;
    }

    public VaultEventDataDTO getEventInfoFull(int eventId) {
        String json = restTemplate.getForObject(urlWizard.getEventInfo(eventId), String.class);
        json = json.replace("\"\"", "null");
        VaultEventDataDTO eventData = new Gson().fromJson(json, VaultEventDataDTO.class);
        eventData.getEvent().setEvent_id(eventId);

        return eventData;
    }

    public VaultResponseDTO postScore(FlightDTO flightDTO) {
        VaultResponseDTO response = restTemplate.getForObject(urlWizard.postScore(flightDTO), VaultResponseDTO.class);
        if (response.getResponse_code() == 0) {
            response.setMessage("Sending data to F3XVault failed");
        } else {
            response.setMessage("Sending data to F3XVault succeeded");
        }
        return response;
    }

    public VaultResponseDTO updateEventRoundStatus(Integer roundNum, Integer eventId) {
        final EventRound eventRound = roundRepository.findEventRound(roundNum, eventId);
        String json = restTemplate.getForObject(urlWizard.updateEventRoundStatus(roundNum, eventId, eventRound.isCancelled()), String.class);
        VaultResponseDTO response = new Gson().fromJson(json, VaultResponseDTO.class);
        if (response.getResponse_code() == 0) {
            response.setMessage("Event round " + roundNum + " status update failed");
        } else {
            response.setMessage("Event round " + roundNum + " status set to " + eventRound.isCancelled() +" on F3XVault");

        }
        return response;
    }


}
