package aviationModelling.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Getter
@AllArgsConstructor
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
    private final Date expirationDate;
    private final Collection<? extends GrantedAuthority> authorities;


}
