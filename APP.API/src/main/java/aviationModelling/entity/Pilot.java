package aviationModelling.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "pilot")
public class Pilot {

    @Id
    @Column(name = "pilot_id")
    private Integer id;

    @Column(name = "event_id")
    private Integer eventId;

    @Column(name = "pilot_bib")
    private String pilotBib;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "pilot_class")
    private String pilotClass;

    private String ama;
    private String fai;

    @Column(name = "fai_license")
    private String faiLicence;

    @Column(name = "team_name")
    private String teamName;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "pilot_id")
    private List<Round> rounds;

//    dla komunikacja dwukierunkowej:

//    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
//    @MapsId("eventId")
//    @JoinColumn(name = "event_id")
//    private Event event;

//    @OneToMany(mappedBy = "pilot",
//            cascade = CascadeType.ALL)
//    private List<Round> rounds;

}
