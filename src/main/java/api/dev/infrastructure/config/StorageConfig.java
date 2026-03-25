package api.dev.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import api.dev.infrastructure.storage.AvatarLocalStorage;
import api.dev.infrastructure.storage.StorageProperties;
import api.dev.infrastructure.storage.AvatarS3Storage;
import api.dev.infrastructure.storage.StorageService;

@Configuration
public class StorageConfig {

    private final StorageProperties props;

    public StorageConfig(StorageProperties props) {
        this.props = props;
    }

    @Bean
    public StorageService avatarStorageService() {
        return switch (props.getStorage()) {
            case "s3"    -> new AvatarS3Storage();
            case "azure" -> throw new UnsupportedOperationException("Azure storage not yet implemented");
            default      -> new AvatarLocalStorage(props.getLocalPath(), props.getBaseUrl());
        };
    }
}
