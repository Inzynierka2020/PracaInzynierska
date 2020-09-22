package aviationModelling.service;

import aviationModelling.dto.UserDTO;
import aviationModelling.entity.auth.Authority;
import aviationModelling.entity.auth.UserDAO;
import aviationModelling.mapper.UserMapper;
import aviationModelling.repository.AuthorityRepository;
import aviationModelling.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private UserMapper userMapper;


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

    public void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    public UserDAO save(UserDTO user) {


        UserDAO newUser = userMapper.toUserDAO(user);

        return userRepository.save(newUser);
    }

    public UserDAO update(UserDTO user) {
        final UserDAO userDAO = userRepository.findByUsername(user.getUsername());
        if (userDAO != null) {
            if (user.getAuthorities() != null) userDAO.setAuthorities(mapToAuthorities(user.getAuthorities()));
            if (user.getUsername() != null) userDAO.setUsername(user.getUsername());
            if (user.getEmail() != null) userDAO.setEmail(user.getEmail());
            if (user.getPassword() != null) userDAO.setPassword(passwordEncoder.encode(user.getUsername()));
            if (user.getVaultLogin() != null) userDAO.setVaultLogin(user.getVaultLogin());
            if (user.getVaultPassword() != null) userDAO.setVaultPassword(user.getVaultPassword());
            if (user.getVaultUrl() != null) userDAO.setVaultUrl(user.getVaultUrl());
            return userRepository.save(userDAO);
        } else {
            return save(user);
        }
    }

    public List<Authority> mapToAuthorities(List<String> roles) {
        List<Authority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(authorityRepository.findByName(role)));
        return authorities;
    }

}
