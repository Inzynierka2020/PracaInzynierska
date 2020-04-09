package aviationModelling.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "event_round")
public class EventRound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_round_id")
    private Integer eventRoundId;

    @Column(name = "round_num")
    private Integer roundNum;

    @Column(name = "event_id")
    private Integer eventId;

    @Column(name = "is_cancelled")
    private boolean isCancelled;

    @Column(name = "is_finished")
    private boolean isFinished;

    @OneToMany(mappedBy = "eventRound", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Flight> flights;

    @ManyToOne
    @JoinColumn(name = "round_num", insertable=false, updatable=false)
    private Round round;

    @ManyToOne
    @JoinColumn(name = "event_id", insertable=false, updatable=false)
    private Event event;


}
