package aviationModelling.service;

import aviationModelling.entity.Event;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface EventService {
    Event findById(int id);
    ResponseEntity<String> save(Event event);
    ResponseEntity<String> updateTotalScore();
    ResponseEntity<String> delete(int eventId);
    ResponseEntity<String> initializeDbWithDataFromVault(int eventId);
    

}
