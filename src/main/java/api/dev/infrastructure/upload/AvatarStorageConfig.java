package api.dev.infrastructure.upload;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AvatarStorageConfig {

    private final AvatarStorageProperties props;

    public AvatarStorageConfig(AvatarStorageProperties props) {
        this.props = props;
    }

    @Bean
    public AvatarStorageService avatarStorageService() {
        return switch (props.getStorage()) {
            case "s3"    -> new AvatarStorageS3Service();
            case "azure" -> throw new UnsupportedOperationException("Azure storage not yet implemented");
            default      -> new AvatarStorageLocalService(props.getLocalPath(), props.getBaseUrl());
        };
    }
}
