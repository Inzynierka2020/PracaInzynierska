package aviationModelling.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "event")
public class Event {

    @Id
    @Column(name = "event_id")
    private Integer eventId;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "event_location")
    private String eventLocation;

    @Column(name = "event_start_date")
    private String eventStartDate;

    @Column(name = "event_end_date")
    private String eventEndDate;

    @Column(name = "event_type")
    private String eventType;

    @Column(name = "number_of_rounds")
    private Integer numberOfRounds;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id")
    private List<Pilot> pilots;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id")
    private List<Pilot> rounds;


//    dla komunikacja dwukierunkowej:

//    @OneToMany(mappedBy = "event",
//            cascade = CascadeType.ALL)
//    private List<Pilot> pilots;
}
