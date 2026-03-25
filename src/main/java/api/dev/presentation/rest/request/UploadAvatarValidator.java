package api.dev.presentation.rest.request;

import api.dev.domain.shared.exception.ValidationException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class UploadAvatarValidator {

    private static final long   MAX_SIZE_BYTES    = 2 * 1024 * 1024; // 2 MB
    private static final List<String> ALLOWED_TYPES = List.of(
        "image/jpeg", "image/png", "image/webp"
    );

    public static void validate(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ValidationException("Avatar file must not be empty");
        }
        if (file.getSize() > MAX_SIZE_BYTES) {
            throw new ValidationException("Avatar file must not exceed 2 MB");
        }
        if (!ALLOWED_TYPES.contains(file.getContentType())) {
            throw new ValidationException("Avatar must be JPEG, PNG or WebP");
        }
    }
}
