package aviationModelling.service;

import aviationModelling.dto.UserDTO;
import aviationModelling.entity.UserDAO;
import aviationModelling.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDAO userDAO = userRepository.findByUsername(username);
        if(userDAO!=null) {
            return new User(userDAO.getUsername(), userDAO.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("UserDAO not found with username: "+ username);
        }
    }

    public UserDAO save(UserDTO user) {
        UserDAO newUser = new UserDAO();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(newUser);
    }

    public List<UserDAO> getUsers() {
        UserDAO userDAO = userRepository.findByUsername("piotrek");
        return userRepository.findAll();
    }
}
