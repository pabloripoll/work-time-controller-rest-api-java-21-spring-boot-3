package api.dev.infrastructure.upload;

import org.springframework.web.multipart.MultipartFile;

public interface AvatarStorageService {
    String store(MultipartFile file, String filename);
}
