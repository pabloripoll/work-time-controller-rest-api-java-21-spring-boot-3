package api.dev.presentation.rest.domain.master;

import api.dev.application.master.usecase.command.UpdateMasterProfileCommand;
import api.dev.application.master.usecase.command.UpdateMasterProfileUseCase;
import api.dev.application.master.usecase.command.UploadMasterAvatarCommand;
import api.dev.application.master.usecase.command.UploadMasterAvatarUseCase;
import api.dev.application.master.usecase.command.DeleteMasterAvatarCommand;
import api.dev.application.master.usecase.command.DeleteMasterAvatarUseCase;
import api.dev.application.master.usecase.query.GetMasterByUserIdQuery;
import api.dev.application.master.usecase.query.GetMasterByUserIdUseCase;
import api.dev.application.user.usecase.command.UpdateUserPasswordCommand;
import api.dev.application.user.usecase.command.UpdateUserPasswordUseCase;
import api.dev.domain.shared.util.FileNameSlugger;
import api.dev.domain.shared.exception.ValidationException;
import api.dev.infrastructure.security.userdetails.AuthenticatedUser;
import api.dev.infrastructure.storage.StorageService;
import api.dev.presentation.rest.request.PasswordValidator;
import api.dev.presentation.rest.request.UploadAvatarValidator;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/master")
@PreAuthorize("hasRole('MASTER')")
public class MasterAccountController {

    private final GetMasterByUserIdUseCase getMasterByUserIdUseCase;
    private final UpdateMasterProfileUseCase updateMasterProfileUseCase;
    private final UpdateUserPasswordUseCase updateUserPasswordUseCase;
    private final StorageService avatarStorageService;
    private final UploadMasterAvatarUseCase uploadMasterAvatarUseCase;
    private final DeleteMasterAvatarUseCase deleteMasterAvatarUseCase;

    public MasterAccountController(
        GetMasterByUserIdUseCase getMasterByUserIdUseCase,
        UpdateMasterProfileUseCase updateMasterProfileUseCase,
        UpdateUserPasswordUseCase updateUserPasswordUseCase,
        StorageService avatarStorageService,
        UploadMasterAvatarUseCase uploadMasterAvatarUseCase,
        DeleteMasterAvatarUseCase deleteMasterAvatarUseCase
    ) {
        this.getMasterByUserIdUseCase = getMasterByUserIdUseCase;
        this.updateMasterProfileUseCase = updateMasterProfileUseCase;
        this.updateUserPasswordUseCase = updateUserPasswordUseCase;
        this.avatarStorageService = avatarStorageService;
        this.uploadMasterAvatarUseCase = uploadMasterAvatarUseCase;
        this.deleteMasterAvatarUseCase = deleteMasterAvatarUseCase;
    }

    @GetMapping("/account/profile")
    public ResponseEntity<?> getProfile(
        @AuthenticationPrincipal AuthenticatedUser authUser
    ) {
        var master = getMasterByUserIdUseCase.execute(new GetMasterByUserIdQuery(authUser.getDomainUser().getId()));

        Map<String, Object> response = new java.util.LinkedHashMap<>();
        response.put("id",         master.id());
        response.put("user_id",    master.userId());
        response.put("nickname",   master.profile() != null ? master.profile().nickname() : null);
        response.put("avatar",     master.profile() != null ? master.profile().avatar()   : null);
        response.put("created_at", master.createdAt().toString());
        response.put("updated_at", master.updatedAt().toString());

        return ResponseEntity.ok(response);
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

        Map<String, Object> response = new java.util.LinkedHashMap<>();
        response.put("id",         master.id());
        response.put("user_id",    master.userId());
        response.put("nickname",   master.profile() != null ? master.profile().nickname() : null);
        response.put("avatar",     master.profile() != null ? master.profile().avatar()   : null);
        response.put("created_at", master.createdAt().toString());
        response.put("updated_at", master.updatedAt().toString());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/account/settings/password")
    public ResponseEntity<?> updatePassword(
            @AuthenticationPrincipal AuthenticatedUser authUser,
            @RequestBody Map<String, String> body
    ) {
        String oldPassword    = body.get("old_password");
        String newPassword    = body.get("new_password");
        String confirmedPassword = body.get("confirmed_password");

        Map<String, String> errors = new LinkedHashMap<>();

        if (oldPassword == null || oldPassword.isBlank()) {
            errors.put("old_password", "Current password is required");
        }

        if (newPassword == null || newPassword.isBlank()) {
            errors.put("new_password", "New password is required");
        } else {
            try {
                PasswordValidator.validate(newPassword);
            } catch (ValidationException e) {
                errors.put("new_password", e.getMessage());
            }
        }

        if (confirmedPassword == null || confirmedPassword.isBlank()) {
            errors.put("confirmed_password", "Password confirmation is required");
        } else if (newPassword != null && !newPassword.equals(confirmedPassword)) {
            errors.put("confirmed_password", "Passwords do not match");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        updateUserPasswordUseCase.execute(new UpdateUserPasswordCommand(
            authUser.getDomainUser().getId(),
            newPassword
        ));

        Map<String, Object> response = new java.util.LinkedHashMap<>();
        response.put("message", "User password updated.");

        return ResponseEntity.ok(response);
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
