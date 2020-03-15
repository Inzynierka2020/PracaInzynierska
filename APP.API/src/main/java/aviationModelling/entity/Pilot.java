package aviationModelling.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "pilot")
public class Pilot {

    @Id
    @Column(name="pilot_id")
    private int id;

    @Column(name = "event_id")
    private int eventId;

    @Column(name="pilot_bib")
    private String pilotBib;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="pilot_class")
    private String pilotClass;

    private String ama;
    private String fai;

    @Column(name="fai_license")
    private String faiLicence;

    @Column(name="team_name")
    private String teamName;
}
