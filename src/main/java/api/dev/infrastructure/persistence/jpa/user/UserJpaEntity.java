package api.dev.infrastructure.persistence.jpa.user;

import api.dev.domain.user.model.entity.UserRole;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class UserJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // EnumType.STRING tells Hibernate to save "MASTER" / "ADMIN" in the DB instead of 0, 1, 2
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "created_by_user_id", nullable = false)
    private Long createdByUserId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at", nullable = true)
    private LocalDateTime deletedAt;

    // --- CONSTRUCTORS ---

    // An empty constructor is strictly required by JPA/Hibernate
    public UserJpaEntity() {
    }

    // --- GETTERS AND SETTERS ---
    // JPA Entities need getters and setters so your Mapper can transfer data
    // to and from your pure Domain Entity.

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public UserRole getRole() { return role; }

    public void setRole(UserRole role) { this.role = role; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public Long getCreatedByUserId() { return createdByUserId; }

    public void setCreatedByUserId(Long createdByUserId) { this.createdByUserId = createdByUserId; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public LocalDateTime getDeletedAt() { return deletedAt; }

    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }

    // --- HOOKS (Lifecycle Callbacks) ---
    // This is the exact equivalent of Symfony's #[ORM\PrePersist] and #[ORM\PreUpdate]

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
