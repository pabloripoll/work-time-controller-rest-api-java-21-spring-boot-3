package api.dev.presentation.rest.domain.shared;

import api.dev.application.master.usecase.command.RefreshMasterAccessLogCommand;
import api.dev.application.master.usecase.command.RefreshMasterAccessLogUseCase;
import api.dev.application.master.usecase.command.TerminateMasterAccessLogCommand;
import api.dev.application.master.usecase.command.TerminateMasterAccessLogUseCase;
import api.dev.domain.user.model.entity.User;
import api.dev.infrastructure.security.userdetails.AuthenticatedUser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final RefreshMasterAccessLogUseCase refreshUseCase;
    private final TerminateMasterAccessLogUseCase terminateUseCase;

    public AuthController(RefreshMasterAccessLogUseCase refreshUseCase,
                           TerminateMasterAccessLogUseCase terminateUseCase) {
        this.refreshUseCase   = refreshUseCase;
        this.terminateUseCase = terminateUseCase;
    }

    @PostMapping("/refresh")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> refresh(HttpServletRequest request,
                                      @AuthenticationPrincipal AuthenticatedUser authUser) {
        String token = extractToken(request);
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "token_not_provided"));
        }

        User user = authUser.getDomainUser();

        if (user.isMaster()) {
            var dto = refreshUseCase.execute(new RefreshMasterAccessLogCommand(
                    token,
                    null, // new token generated inside use case — see note below
                    LocalDateTime.now().plusHours(1)
            ));
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of(
                    "token",         dto.token(),
                    "expires_in",    3600,
                    "token_expired", token
            ));
        }

        // TODO: admin and employee refresh

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", "unsupported_role"));
    }

    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> logout(HttpServletRequest request,
                                     @AuthenticationPrincipal AuthenticatedUser authUser) {
        String token = extractToken(request);
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "token_not_provided"));
        }

        User user = authUser.getDomainUser();

        if (user.isMaster()) {
            terminateUseCase.execute(new TerminateMasterAccessLogCommand(token));
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(Map.of("token_expired", token));
        }

        // TODO: admin and employee logout

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", "unsupported_role"));
    }

    @GetMapping("/whoami")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> whoami(@AuthenticationPrincipal AuthenticatedUser authUser) {
        User user = authUser.getDomainUser();
        return ResponseEntity.ok(Map.of(
                "id",    user.getId(),
                "email", user.getEmail().value(),
                "role",  user.getRole().name()
        ));
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
