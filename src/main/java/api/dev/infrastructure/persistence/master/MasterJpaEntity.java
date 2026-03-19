package api.dev.infrastructure.persistence.master;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "masters")
public class MasterJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "is_banned", nullable = false)
    private boolean isBanned;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "master", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private MasterProfileJpaEntity profile;

    // --- Getters & Setters ---

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public boolean isActive() { return isActive; }

    public void setActive(boolean active) { isActive = active; }

    public boolean isBanned() { return isBanned; }

    public void setBanned(boolean banned) { isBanned = banned; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public MasterProfileJpaEntity getProfile() { return profile; }

    public void setProfile(MasterProfileJpaEntity profile) { this.profile = profile; }
}
