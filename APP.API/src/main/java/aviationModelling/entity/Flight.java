package aviationModelling.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "flight")
public class Flight implements Serializable {

    @EmbeddedId
    private FlightId flightId;

    private Integer penalty;

    @Column(name = "order_num")
    private Integer order;

    @Column(name="group_num")
    private String group;

//    auto-generated in db using hibernate
    @CreationTimestamp
    @Column(name = "flight_time")
    private LocalDateTime flightTime;

    @Column(name = "wind_avg")
    private Float windAvg;

    @Column(name = "dir_avg")
    private Float dirAvg;

    private Float seconds;

    private Float sub1;
    private Float sub2;
    private Float sub3;
    private Float sub4;
    private Float sub5;
    private Float sub6;
    private Float sub7;
    private Float sub8;
    private Float sub9;
    private Float sub10;
    private Float sub11;

    private boolean dns;
    private boolean dnf;

    private Float score;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "event_round_id", insertable=false, updatable=false)
    private EventRound eventRound;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "event_pilot_id", insertable=false, updatable=false)
    private EventPilot eventPilot;


    @Embeddable
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class FlightId implements Serializable {

        @Column(name = "event_pilot_id")
        private Integer eventPilotId;

        @Column(name = "event_round_id")
        private Integer eventRoundId;
    }
}
