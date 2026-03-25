package api.dev.presentation.rest.domain.master;

import api.dev.application.master.usecase.command.UpdateMasterProfileCommand;
import api.dev.application.master.usecase.command.UpdateMasterProfileUseCase;
import api.dev.application.master.usecase.command.UploadMasterAvatarCommand;
import api.dev.application.master.usecase.command.UploadMasterAvatarUseCase;
import api.dev.application.master.usecase.command.DeleteMasterAvatarCommand;
import api.dev.application.master.usecase.command.DeleteMasterAvatarUseCase;
import api.dev.application.master.usecase.query.GetMasterByUserIdQuery;
import api.dev.application.master.usecase.query.GetMasterByUserIdUseCase;
import api.dev.domain.shared.util.FileNameSlugger;
import api.dev.infrastructure.security.userdetails.AuthenticatedUser;
import api.dev.infrastructure.storage.StorageService;
import api.dev.presentation.rest.request.UploadAvatarValidator;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/master")
@PreAuthorize("hasRole('MASTER')")
public class MasterAccountController {

    private final GetMasterByUserIdUseCase getMasterByUserIdUseCase;
    private final UpdateMasterProfileUseCase updateMasterProfileUseCase;
    private final StorageService avatarStorageService;
    private final UploadMasterAvatarUseCase uploadMasterAvatarUseCase;
    private final DeleteMasterAvatarUseCase deleteMasterAvatarUseCase;

    public MasterAccountController(
        GetMasterByUserIdUseCase getMasterByUserIdUseCase,
        UpdateMasterProfileUseCase updateMasterProfileUseCase,
        StorageService avatarStorageService,
        UploadMasterAvatarUseCase uploadMasterAvatarUseCase,
        DeleteMasterAvatarUseCase deleteMasterAvatarUseCase
    ) {
        this.getMasterByUserIdUseCase = getMasterByUserIdUseCase;
        this.updateMasterProfileUseCase = updateMasterProfileUseCase;
        this.avatarStorageService = avatarStorageService;
        this.uploadMasterAvatarUseCase = uploadMasterAvatarUseCase;
        this.deleteMasterAvatarUseCase = deleteMasterAvatarUseCase;
    }

    @GetMapping("/account/profile")
    public ResponseEntity<?> getProfile(
        @AuthenticationPrincipal AuthenticatedUser authUser
    ) {
        var master = getMasterByUserIdUseCase.execute(new GetMasterByUserIdQuery(authUser.getDomainUser().getId()));

        Map<String, Object> data = new java.util.LinkedHashMap<>();
        data.put("id",         master.id());
        data.put("user_id",    master.userId());
        data.put("nickname",   master.profile() != null ? master.profile().nickname() : null);
        data.put("avatar",     master.profile() != null ? master.profile().avatar()   : null);
        data.put("created_at", master.createdAt().toString());
        data.put("updated_at", master.updatedAt().toString());

        return ResponseEntity.ok(Map.of("status", "success", "data", data));
    }

    @PatchMapping("/account/settings/profile")
    public ResponseEntity<?> updateProfile(
        @AuthenticationPrincipal AuthenticatedUser authUser,
        @RequestBody Map<String, String> body
    ) {
        var master = getMasterByUserIdUseCase.execute(new GetMasterByUserIdQuery(authUser.getDomainUser().getId()));

        updateMasterProfileUseCase.execute(new UpdateMasterProfileCommand(
            master.id(),
            body.get("nickname")
        ));

        return ResponseEntity.ok(Map.of("status", "success", "message", "Profile updated"));
    }

    @PatchMapping("/account/settings/password")
    public ResponseEntity<?> updatePassword(
        @AuthenticationPrincipal AuthenticatedUser authUser,
        @RequestBody Map<String, String> body
    ) {
        return ResponseEntity.ok(Map.of("status", "success", "message", "Password updated"));
    }

    @PostMapping(value = "/account/settings/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadAvatar(
        @AuthenticationPrincipal AuthenticatedUser authUser,
        @RequestParam("file") MultipartFile file
    ) {
        UploadAvatarValidator.validate(file);  // ← throws ValidationException → caught by GlobalExceptionHandler → 422

        var master = getMasterByUserIdUseCase.execute(new GetMasterByUserIdQuery(authUser.getDomainUser().getId()));

        String filename  = FileNameSlugger.slug(file.getOriginalFilename());
        String avatarUrl = avatarStorageService.store(file, filename);

        String savedUrl = uploadMasterAvatarUseCase.execute(
                new UploadMasterAvatarCommand(master.id(), avatarUrl));

        return ResponseEntity.ok(Map.of(
                "status", "success",
                "data",   Map.of("avatar_url", savedUrl)
        ));
    }

    @DeleteMapping("/account/settings/avatar")
    public ResponseEntity<?> deleteAvatar(
        @AuthenticationPrincipal AuthenticatedUser authUser
    ) {
        var master = getMasterByUserIdUseCase.execute(new GetMasterByUserIdQuery(authUser.getDomainUser().getId()));

        deleteMasterAvatarUseCase.execute(new DeleteMasterAvatarCommand(master.id()));

        return ResponseEntity.ok(Map.of("status", "success", "message", "Avatar removed"));
    }
}
