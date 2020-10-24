package aviationModelling.entity.auth;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "db_user")
public class UserDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name="username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
//    @JsonIgnore
    private String password;

    @Column(name = "vault_login")
    private String vaultLogin;

    @Column(name = "vault_password")
    private String vaultPassword;

    @Column(name = "vault_url")
    private String vaultUrl;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_authorities",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private List<Authority> authorities;

}
