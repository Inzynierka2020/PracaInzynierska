package aviationModelling.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "round")
public class Round implements Serializable {

    @EmbeddedId
    private RoundId roundId;

    @Column(name = "event_id")
    private Integer eventId;

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

    private Boolean dns;
    private Boolean dnf;

//    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
//    @MapsId("pilotId")
//    @JoinColumn(name = "pilot_id")
//    private Pilot pilot;

    @Embeddable
    @Data
    public static class RoundId implements Serializable {

        @Column(name = "pilot_id")
        private int pilotId;

        @Column(name = "round_num")
        private int roundNum;
    }
}
