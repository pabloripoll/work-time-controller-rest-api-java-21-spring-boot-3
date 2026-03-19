package api.dev.presentation.rest.domain.master;

import api.dev.application.master.usecase.command.UpdateMasterProfileCommand;
import api.dev.application.master.usecase.command.UpdateMasterProfileUseCase;
import api.dev.application.master.usecase.query.GetMasterByUserIdQuery;
import api.dev.application.master.usecase.query.GetMasterByUserIdUseCase;
import api.dev.infrastructure.security.userdetails.AuthenticatedUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/master")
@PreAuthorize("hasRole('MASTER')")
public class MasterAccountController {

    private final GetMasterByUserIdUseCase getMasterByUserIdUseCase;
    private final UpdateMasterProfileUseCase updateProfileUseCase;

    public MasterAccountController(GetMasterByUserIdUseCase getMasterByUserIdUseCase,
                                    UpdateMasterProfileUseCase updateProfileUseCase) {
        this.getMasterByUserIdUseCase = getMasterByUserIdUseCase;
        this.updateProfileUseCase     = updateProfileUseCase;
    }

    @GetMapping("/account/profile")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal AuthenticatedUser authUser) {
        var master = getMasterByUserIdUseCase.execute(
                new GetMasterByUserIdQuery(authUser.getDomainUser().getId()));

        return ResponseEntity.ok(Map.of(
                "status", "success",
                "data", Map.of(
                        "id",         master.id(),
                        "user_id",    master.userId(),
                        "nickname",   master.profile() != null ? master.profile().nickname() : null,
                        "avatar",     master.profile() != null ? master.profile().avatar()   : null,
                        "created_at", master.createdAt().toString(),
                        "updated_at", master.updatedAt().toString()
                )
        ));
    }

    @PatchMapping("/account/settings/profile")
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal AuthenticatedUser authUser,
                                            @RequestBody Map<String, String> body) {
        var master = getMasterByUserIdUseCase.execute(
                new GetMasterByUserIdQuery(authUser.getDomainUser().getId()));

        updateProfileUseCase.execute(new UpdateMasterProfileCommand(
                master.id(),
                body.get("nickname"),
                body.get("avatar")
        ));

        return ResponseEntity.ok(Map.of("status", "success", "message", "Profile updated"));
    }

    @PatchMapping("/account/settings/password")
    public ResponseEntity<?> updatePassword(@AuthenticationPrincipal AuthenticatedUser authUser,
                                             @RequestBody Map<String, String> body) {
        // TODO: implement ChangePasswordUseCase
        return ResponseEntity.ok(Map.of("status", "success", "message", "Password updated"));
    }
}
