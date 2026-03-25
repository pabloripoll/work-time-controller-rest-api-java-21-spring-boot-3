package api.dev.infrastructure.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "avatar")
public class StorageProperties {

    private String storage;    // "local" or "s3" or "azure"
    private String localPath;
    private String baseUrl;

    // getters & setters
    public String getStorage()            { return storage; }

    public void setStorage(String v)      { this.storage = v; }

    public String getLocalPath()          { return localPath; }

    public void setLocalPath(String v)    { this.localPath = v; }

    public String getBaseUrl()            { return baseUrl; }

    public void setBaseUrl(String v)      { this.baseUrl = v; }
}