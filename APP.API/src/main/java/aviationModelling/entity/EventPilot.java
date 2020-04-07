package aviationModelling.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter @Setter
@Table(name = "event_pilot")
public class EventPilot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_pilot_id")
    private Integer eventPilotId;

    @Column(name = "pilot_id")
    private Integer pilotId;

    @Column(name = "event_id")
    private Integer eventId;

    private Float discarded1;
    private Float discarded2;
    private Float score;

    @OneToMany(mappedBy = "eventPilot", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Flight> flights;

    @ManyToOne
    @JoinColumn(name = "event_id", insertable=false, updatable=false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "pilot_id", insertable=false, updatable=false)
    private Pilot pilot;
}
