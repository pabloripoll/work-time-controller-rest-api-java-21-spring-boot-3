package api.dev.infrastructure.upload;

import org.springframework.web.multipart.MultipartFile;

public class AvatarStorageS3Service implements AvatarStorageService {

    @Override
    public String store(MultipartFile file, String filename) {
        throw new UnsupportedOperationException("S3 storage not yet implemented");
    }
}
