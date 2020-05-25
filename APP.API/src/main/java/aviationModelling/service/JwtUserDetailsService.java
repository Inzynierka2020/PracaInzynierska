package aviationModelling.service;

import aviationModelling.dto.UserDTO;
import aviationModelling.entity.auth.Authority;
import aviationModelling.entity.auth.UserDAO;
import aviationModelling.repository.AuthorityRepository;
import aviationModelling.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDAO userDAO = userRepository.findByUsername(username);
        if(userDAO!=null) {
            return new User(userDAO.getUsername(), userDAO.getPassword(), mapListToCollection(userDAO.getAuthorities()));
        } else {
            throw new UsernameNotFoundException("UserDAO not found with username: "+ username);
        }
    }

    private Collection<? extends GrantedAuthority> mapListToCollection(List<Authority> authorities) {
        return authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.getName())).collect(Collectors.toList());
    }

    public UserDAO save(UserDTO user) {
        UserDAO newUser = new UserDAO();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setAuthorities(mapToAuthorities(user.getAuthorities()));
        return userRepository.save(newUser);
    }

    private List<Authority> mapToAuthorities(List<String> roles) {
        List<Authority> authorities = new ArrayList<>();
        roles.forEach(role-> authorities.add(authorityRepository.findByName(role)));
        return authorities;
    }

}
