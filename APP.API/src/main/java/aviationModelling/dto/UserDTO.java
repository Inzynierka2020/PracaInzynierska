package aviationModelling.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.List;

@Getter
@Setter
public class UserDTO {

    private String username;
    private String email;
    private String password;
    private String vaultLogin;
    private String vaultPassword;
    private String vaultUrl;
    private List<String> authorities;

}
