package aviationModelling.dto;

import aviationModelling.validators.UniqueUsername;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
public class UserDTO {

    @UniqueUsername
    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;


    private String vaultLogin;
    private String vaultPassword;
    private String vaultUrl;
    private List<String> authorities;

}
