package aviationModelling.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name="event")
public class Event {

    @Id
    @Column(name="event_id")
    private int eventId;

    @Column(name="event_name")
    private String eventName;

    @Column(name="event_location")
    private String eventLocation;

    @Column(name="event_start_date")
    private String eventStartDate;

    @Column(name="event_end_date")
    private String eventEndDate;

    @Column(name = "event_type")
    private String eventType;

    @Column(name="number_of_rounds")
    private int numberOfRounds;
}
