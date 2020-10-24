package aviationModelling.service;

import aviationModelling.dto.FlightDTO;
import aviationModelling.dto.RoundDTO;
import aviationModelling.entity.auth.UserDAO;
import aviationModelling.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UrlWizard {

    private UserRepository userRepository;

    public UrlWizard(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getEventInfo(Integer event_id) {
        Map<String, String> params = new HashMap<>();
        params.put("function", "getEventInfoFull");
        params.put("event_id", event_id.toString());
        return urlBuilder(params);
    }

    public String postScore(FlightDTO flightDTO) {
        Map<String, String> params = new HashMap<>();
        params.put("function", "postScore");
        params.put("event_id", flightDTO.getEventId().toString());
        params.put("pilot_id", flightDTO.getPilotId().toString());
        params.put("round", flightDTO.getRoundNum().toString());
        params.put("seconds", flightDTO.getSeconds().toString());
        params.put("sub1", flightDTO.getSub1().toString());
        params.put("sub2", flightDTO.getSub2().toString());
        params.put("sub3", flightDTO.getSub3().toString());
        params.put("sub4", flightDTO.getSub4().toString());
        params.put("sub5", flightDTO.getSub5().toString());
        params.put("sub6", flightDTO.getSub6().toString());
        params.put("sub7", flightDTO.getSub7().toString());
        params.put("sub8", flightDTO.getSub8().toString());
        params.put("sub9", flightDTO.getSub9().toString());
        params.put("sub10", flightDTO.getSub10().toString());
        params.put("sub11", flightDTO.getSub11().toString());
        if (flightDTO.getPenalty() != null && flightDTO.getPenalty() != 0) {
            params.put("penalty", flightDTO.getPenalty().toString());
        }
        if (flightDTO.getFlightTime() != null) {
            params.put("flight_time", flightDTO.getFlightTime().toString());
        }
        if (flightDTO.getWindAvg() != null) {
            params.put("wind_avg", flightDTO.getWindAvg().toString());
        }
        if (flightDTO.getDirAvg() != null) {
            params.put("dir_avg", flightDTO.getDirAvg().toString());
        }
        if (flightDTO.getGroup() != null) {
            params.put("group", flightDTO.getGroup());
        }
        if (flightDTO.getOrder() != null) {
            params.put("order", flightDTO.getOrder().toString());
        }
        if (flightDTO.isDns() == true) {
            params.put("dns", "true");
        }
        if (flightDTO.isDnf() == true) {
            params.put("dnf", "true");
        }
        return urlBuilder(params);
    }

    public String updateEventRoundStatus(Integer roundNum, Integer eventId, boolean isCancelled) {
        String eventRoundScoreStatus = isCancelled == true ? "0" : "1";
        Map<String, String> params = new HashMap<>();
        params.put("function", "updateEventRoundStatus");
        params.put("event_id", eventId.toString());
        params.put("round_number", roundNum.toString());
        params.put("event_round_score_status", eventRoundScoreStatus);
        return urlBuilder(params);
    }

    private String urlBuilder(Map<String, String> params) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDAO user = userRepository.findByUsername(username);

        StringBuilder result = new StringBuilder();
        result.append(user.getVaultUrl());
        result.append("login=" + user.getVaultLogin());
        result.append("&");
        result.append("password=" + user.getVaultPassword());
        result.append("&");
        result.append("output_format=" + "json");
        result.append("&");

//        zbuduj URL ze wszystkich parametrow
        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(entry.getKey());
            result.append("=");
            result.append(entry.getValue());
            result.append("&");
        }
        String stringResult = result.toString();

        // usun & na koncu Stringa
        return stringResult.substring(0, stringResult.length() - 1);
    }
}
