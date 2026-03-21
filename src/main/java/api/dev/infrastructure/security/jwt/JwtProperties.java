package api.dev.infrastructure.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String secret;
    private long ttl = 3600; // seconds — default 1 hour

    public String getSecret() { return secret; }

    public void setSecret(String secret) { this.secret = secret; }

    public long getTtl() { return ttl; }

    public void setTtl(long ttl) { this.ttl = ttl; }
}
