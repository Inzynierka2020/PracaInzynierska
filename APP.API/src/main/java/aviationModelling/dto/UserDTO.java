package aviationModelling.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {

    private String username;
    private String email;
    private String password;
    private List<String> authorities;

}
