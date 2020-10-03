package aviationModelling.restcontroller;

import aviationModelling.dto.ConfigDTO;
import aviationModelling.dto.UserDTO;
import aviationModelling.entity.auth.UserDAO;
import aviationModelling.service.JwtUserDetailsService;
//import aviationModelling.service.UserAccountService;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class RestUserController {

    private JwtUserDetailsService userDetailsService;

    public RestUserController(JwtUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCredentials(@RequestBody UserDTO user) {
        return ResponseEntity.ok(userDetailsService.update(user));
    }

    @GetMapping("/vault-config")
    public ResponseEntity<?> getVaultConfig() {
        return ResponseEntity.ok(userDetailsService.getVaultConfig());
    }
}
