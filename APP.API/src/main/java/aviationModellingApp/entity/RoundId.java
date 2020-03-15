package aviationModellingApp.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RoundId implements Serializable {

    @Column(name = "pilot_id")
    private int pilotId;


    @Column(name = "round_num")
    private int roundNum;

    public RoundId() {
    }

    public RoundId(int pilotId, int roundNum) {
        this.pilotId = pilotId;
        this.roundNum = roundNum;
    }

    public int getPilotId() {
        return pilotId;
    }

    public void setPilotId(int pilotId) {
        this.pilotId = pilotId;
    }

    public int getRound() {
        return roundNum;
    }

    public void setRound(int roundNum) {
        this.roundNum = roundNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoundId roundId = (RoundId) o;

        if (pilotId != roundId.pilotId) return false;
        return roundNum == roundId.roundNum;
    }

    @Override
    public int hashCode() {
        int result = pilotId;
        result = 31 * result + roundNum;
        return result;
    }
}
