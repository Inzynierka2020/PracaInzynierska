package aviationModelling.converter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UrlWizard {

    @Value("${vault.login}")
    private String login;

    @Value("${vault.password}")
    private String password;

    public String getEventPilots(Integer event_id) {
        Map<String, String> params = new HashMap<>();
        params.put("login", login);
        params.put("password", password);
        params.put("function", "getEventPilots");
        params.put("event_id", event_id.toString());
        return urlBuilder(params);
    }

    public String getEventInfo(Integer event_id) {
        Map<String, String> params = new HashMap<>();
        params.put("login", login);
        params.put("password", password);
        params.put("function", "getEventInfo");
        params.put("event_id", event_id.toString());
        return urlBuilder(params);
    }

    private String urlBuilder(Map<String, String> params) {
        StringBuilder result = new StringBuilder();
        result.append("http://www.f3xvault.com/api.php?");

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
