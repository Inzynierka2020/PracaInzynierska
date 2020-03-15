package aviationModellingApp.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
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

    @Column(name = "flight_time")
    private Float flightTime;

    @Column(name = "wind_avg")
    private Float windAvg;

    @Column(name = "dir_avg")
    private Float dirAvg;

    @Column(name = "seconds")
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

    public Round() {
    }


    public RoundId getRoundId() {
        return roundId;
    }

    public void setRoundId(RoundId roundId) {
        this.roundId = roundId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }


    public Integer getPenalty() {
        return penalty;
    }

    public void setPenalty(Integer penalty) {
        this.penalty = penalty;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Float getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(Float flightTime) {
        this.flightTime = flightTime;
    }

    public Float getWindAvg() {
        return windAvg;
    }

    public void setWindAvg(Float windAvg) {
        this.windAvg = windAvg;
    }

    public Float getDirAvg() {
        return dirAvg;
    }

    public void setDirAvg(Float dirAvg) {
        this.dirAvg = dirAvg;
    }

    public Float getSeconds() {
        return seconds;
    }

    public void setSeconds(Float seconds) {
        this.seconds = seconds;
    }

    public Float getSub1() {
        return sub1;
    }

    public void setSub1(Float sub1) {
        this.sub1 = sub1;
    }

    public Float getSub2() {
        return sub2;
    }

    public void setSub2(Float sub2) {
        this.sub2 = sub2;
    }

    public Float getSub3() {
        return sub3;
    }

    public void setSub3(Float sub3) {
        this.sub3 = sub3;
    }

    public Float getSub4() {
        return sub4;
    }

    public void setSub4(Float sub4) {
        this.sub4 = sub4;
    }

    public Float getSub5() {
        return sub5;
    }

    public void setSub5(Float sub5) {
        this.sub5 = sub5;
    }

    public Float getSub6() {
        return sub6;
    }

    public void setSub6(Float sub6) {
        this.sub6 = sub6;
    }

    public Float getSub7() {
        return sub7;
    }

    public void setSub7(Float sub7) {
        this.sub7 = sub7;
    }

    public Float getSub8() {
        return sub8;
    }

    public void setSub8(Float sub8) {
        this.sub8 = sub8;
    }

    public Float getSub9() {
        return sub9;
    }

    public void setSub9(Float sub9) {
        this.sub9 = sub9;
    }

    public Float getSub10() {
        return sub10;
    }

    public void setSub10(Float sub10) {
        this.sub10 = sub10;
    }

    public Float getSub11() {
        return sub11;
    }

    public void setSub11(Float sub11) {
        this.sub11 = sub11;
    }

    public boolean isDns() {
        return dns;
    }

    public void setDns(boolean dns) {
        this.dns = dns;
    }

    public boolean isDnf() {
        return dnf;
    }

    public void setDnf(boolean dnf) {
        this.dnf = dnf;
    }
}
