package api.dev.infrastructure.persistence.jpa.master;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "master_access_logs")
public class MasterAccessLogJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "is_terminated", nullable = false)
    private boolean isTerminated;

    @Column(name = "is_expired", nullable = false)
    private boolean isExpired;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "refresh_count", nullable = false)
    private int refreshCount;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "user_agent", columnDefinition = "TEXT")
    private String userAgent;

    @Column(name = "requests_count", nullable = false)
    private int requestsCount;

    @Column(name = "payload", columnDefinition = "JSON")
    private String payload;

    @Column(name = "token", columnDefinition = "TEXT", nullable = false)
    private String token;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // --- Getters & Setters (omitted for brevity, generate with IDE) ---
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public boolean isTerminated() { return isTerminated; }

    public void setTerminated(boolean terminated) { isTerminated = terminated; }

    public boolean isExpired() { return isExpired; }

    public void setExpired(boolean expired) { isExpired = expired; }

    public LocalDateTime getExpiresAt() { return expiresAt; }

    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }

    public int getRefreshCount() { return refreshCount; }

    public void setRefreshCount(int refreshCount) { this.refreshCount = refreshCount; }

    public String getIpAddress() { return ipAddress; }

    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public String getUserAgent() { return userAgent; }

    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }

    public int getRequestsCount() { return requestsCount; }

    public void setRequestsCount(int requestsCount) { this.requestsCount = requestsCount; }

    public String getPayload() { return payload; }

    public void setPayload(String payload) { this.payload = payload; }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

}
