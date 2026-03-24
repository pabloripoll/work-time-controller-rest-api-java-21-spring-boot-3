package api.dev.infrastructure.upload;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AvatarStorageLocalService implements AvatarStorageService {

    private final String localPath;
    private final String baseUrl;

    public AvatarStorageLocalService(String localPath, String baseUrl) {
        this.localPath = localPath;
        this.baseUrl   = baseUrl;
    }

    @Override
    public String store(MultipartFile file, String filename) {
        try {
            Path dir = Paths.get(localPath);
            Files.createDirectories(dir);
            Files.copy(file.getInputStream(), dir.resolve(filename));

            return baseUrl + "/" + filename;

        } catch (IOException e) {
            throw new RuntimeException("Failed to store avatar locally", e);
        }
    }
}
