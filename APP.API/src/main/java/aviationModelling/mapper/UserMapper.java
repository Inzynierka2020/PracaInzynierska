package aviationModelling.mapper;

import aviationModelling.dto.UserDTO;
import aviationModelling.entity.auth.Authority;
import aviationModelling.entity.auth.UserDAO;
import aviationModelling.service.JwtUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UserMapper {

    private PasswordEncoder passwordEncoder;
    private JwtUserDetailsService userDetailsService;

    public UserMapper(PasswordEncoder passwordEncoder, JwtUserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    public UserDAO toUserDAO(UserDTO dto) {
        return UserDAO.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .authorities(mapToAuthorities(dto))
                .vaultLogin(dto.getVaultLogin())
                .vaultUrl(dto.getVaultUrl())
                .vaultPassword(dto.getVaultPassword())
                .build();
    }

    private List<Authority> mapToAuthorities(UserDTO dto) {

        if(dto.getAuthorities()==null) {
            dto.setAuthorities(Arrays.asList("USER"));
        }
        return userDetailsService.mapToAuthorities(dto.getAuthorities());
    }

}


