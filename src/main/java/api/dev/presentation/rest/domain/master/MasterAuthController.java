package api.dev.presentation.rest.domain.master;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/master/auth")
public class MasterAuthController {

    /**
     * This method is never reached.
     * The SecurityConfig login filter intercepts POST /api/v1/master/auth/login
     * and delegates to AuthSuccessHandler / AuthFailureHandler — exactly like
     * Symfony's json_login firewall.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login() {
        return ResponseEntity.ok().build();
    }
}
