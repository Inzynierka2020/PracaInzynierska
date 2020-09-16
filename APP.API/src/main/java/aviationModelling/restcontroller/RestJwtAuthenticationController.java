package aviationModelling.restcontroller;

//import aviationModelling.config.JwtTokenUtil;
import aviationModelling.config.JwtTokenUtil;
import aviationModelling.dto.UserDTO;
import aviationModelling.model.JwtRequest;
import aviationModelling.model.JwtResponse;
import aviationModelling.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class RestJwtAuthenticationController {


    private JwtTokenUtil jwtTokenUtil;

    private JwtUserDetailsService userDetailsService;

    public RestJwtAuthenticationController(JwtTokenUtil jwtTokenUtil, JwtUserDetailsService userDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        userDetailsService.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        final Date expirationDate = jwtTokenUtil.getExpirationDateFromToken(token);
        final Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        return ResponseEntity.ok(new JwtResponse(token, expirationDate, authorities));
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody UserDTO user) {
        return ResponseEntity.ok(userDetailsService.save(user));
    }






}
