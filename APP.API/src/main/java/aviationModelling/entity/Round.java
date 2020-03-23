package aviationModelling.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Round {

    @Id
    @Column(name = "round_num")
    private Integer roundNum;

    @Column(name = "is_cancelled")
    private Boolean isCancelled;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "round_num")
    private List<Flight> flights;


}
