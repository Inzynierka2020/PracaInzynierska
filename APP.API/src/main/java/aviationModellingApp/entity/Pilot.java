package aviationModellingApp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
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

    public Pilot() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPilotBib() {
        return pilotBib;
    }

    public void setPilotBib(String pilotBib) {
        this.pilotBib = pilotBib;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPilotClass() {
        return pilotClass;
    }

    public void setPilotClass(String pilotClass) {
        this.pilotClass = pilotClass;
    }

    public String getAma() {
        return ama;
    }

    public void setAma(String ama) {
        this.ama = ama;
    }

    public String getFai() {
        return fai;
    }

    public void setFai(String fai) {
        this.fai = fai;
    }

    public String getFaiLicence() {
        return faiLicence;
    }

    public void setFaiLicence(String faiLicence) {
        this.faiLicence = faiLicence;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}
