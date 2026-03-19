package api.dev.domain.master.model.entity;

import api.dev.domain.user.model.entity.User;
import java.time.LocalDateTime;
import java.util.Map;

public class MasterAccessLog {

    private Long id;
    private User user;
    private String token;
    private LocalDateTime expiresAt;
    private String ipAddress;
    private String userAgent;
    private Map<String, Object> payload;

    private boolean isTerminated  = false;
    private boolean isExpired     = false;
    private int refreshCount      = 0;
    private int requestsCount     = 0;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private MasterAccessLog() {}

    // ------------------------------------------------------------------ //
    // Factory: new log entry (on login)
    // ------------------------------------------------------------------ //
    public static MasterAccessLog create(User user, String token, LocalDateTime expiresAt,
                                          String ipAddress, String userAgent,
                                          Map<String, Object> payload) {
        MasterAccessLog log = new MasterAccessLog();
        log.user          = user;
        log.token         = token;
        log.expiresAt     = expiresAt;
        log.ipAddress     = ipAddress;
        log.userAgent     = userAgent;
        log.payload       = payload;
        log.createdAt     = LocalDateTime.now();
        log.updatedAt     = LocalDateTime.now();
        return log;
    }

    // ------------------------------------------------------------------ //
    // Factory: load from persistence
    // ------------------------------------------------------------------ //
    public static MasterAccessLog reconstitute(Long id, User user, String token,
                                                LocalDateTime expiresAt, String ipAddress,
                                                String userAgent, Map<String, Object> payload,
                                                boolean isTerminated, boolean isExpired,
                                                int refreshCount, int requestsCount,
                                                LocalDateTime createdAt, LocalDateTime updatedAt) {
        MasterAccessLog log   = new MasterAccessLog();
        log.id            = id;
        log.user          = user;
        log.token         = token;
        log.expiresAt     = expiresAt;
        log.ipAddress     = ipAddress;
        log.userAgent     = userAgent;
        log.payload       = payload;
        log.isTerminated  = isTerminated;
        log.isExpired     = isExpired;
        log.refreshCount  = refreshCount;
        log.requestsCount = requestsCount;
        log.createdAt     = createdAt;
        log.updatedAt     = updatedAt;
        return log;
    }

    // ------------------------------------------------------------------ //
    // Business methods (yours — kept as-is, they are correct)
    // ------------------------------------------------------------------ //
    public void incrementRequestCount() {
        this.requestsCount++;
        this.updatedAt = LocalDateTime.now();
    }

    public void refresh(String newToken, LocalDateTime newExpiresAt) {
        this.token        = newToken;
        this.expiresAt    = newExpiresAt;
        this.refreshCount++;
        this.updatedAt    = LocalDateTime.now();
    }

    public void terminate() {
        this.isTerminated = true;
        this.updatedAt    = LocalDateTime.now();
    }

    public void markAsExpired() {
        this.isExpired    = true;
        this.updatedAt    = LocalDateTime.now();
    }

    public boolean checkAndMarkExpired() {
        if (LocalDateTime.now().isAfter(this.expiresAt) && !this.isExpired) {
            this.markAsExpired();
            return true;
        }
        return this.isExpired;
    }

    // ------------------------------------------------------------------ //
    // Getters
    // ------------------------------------------------------------------ //
    public Long getId()                       { return id; }
    public void setId(Long id)                { this.id = id; } // only for JPA mapper

    public User getUser()                     { return user; }
    public String getToken()                  { return token; }
    public LocalDateTime getExpiresAt()       { return expiresAt; }
    public String getIpAddress()              { return ipAddress; }
    public String getUserAgent()              { return userAgent; }
    public Map<String, Object> getPayload()   { return payload; }
    public boolean isTerminated()             { return isTerminated; }
    public boolean isExpired()                { return isExpired; }
    public int getRefreshCount()              { return refreshCount; }
    public int getRequestsCount()             { return requestsCount; }
    public LocalDateTime getCreatedAt()       { return createdAt; }
    public LocalDateTime getUpdatedAt()       { return updatedAt; }
}

