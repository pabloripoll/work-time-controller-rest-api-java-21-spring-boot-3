package api.dev.domain.master.model.entity;

import api.dev.domain.user.model.entity.User;
import java.time.LocalDateTime;

public class Master {

    private Long id;
    private User user;
    private boolean isActive;
    private boolean isBanned;
    private MasterProfile profile;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ------------------------------------------------------------------ //
    // Private constructor — force use of factory methods (DDD)
    // ------------------------------------------------------------------ //
    private Master() {}

    /**
     * Factory: brand-new Master (registration, seeder).
     * ID is null — DB will assign it.
     */
    public static Master create(User user) {
        Master master    = new Master();
        master.user      = user;
        master.isActive  = false;
        master.isBanned  = false;
        master.createdAt = LocalDateTime.now();
        master.updatedAt = LocalDateTime.now();

        return master;
    }

    /**
     * Factory: reconstitute from persistence.
     * All fields are known, including DB-assigned ID.
     */
    public static Master reconstitute(
        Long id,
        User user,
        boolean isActive,
        boolean isBanned,
        MasterProfile profile,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {
        Master master    = new Master();
        master.id        = id;
        master.user      = user;
        master.isActive  = isActive;
        master.isBanned  = isBanned;
        master.profile   = profile;
        master.createdAt = createdAt;
        master.updatedAt = updatedAt;

        return master;
    }

    // ------------------------------------------------------------------ //
    // Business behaviour
    // ------------------------------------------------------------------ //
    public void activate() {
        this.isActive  = true;
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        this.isActive  = false;
        this.updatedAt = LocalDateTime.now();
    }

    public void ban() {
        this.isBanned  = true;
        this.updatedAt = LocalDateTime.now();
    }

    public void unban() {
        this.isBanned  = false;
        this.updatedAt = LocalDateTime.now();
    }

    // ------------------------------------------------------------------ //
    // Getters — no raw setters on business state (use methods above)
    // ------------------------------------------------------------------ //
    public Long getId()               { return id; }

    public void setId(Long id)        { this.id = id; } // only for JPA mapper

    public User getUser()             { return user; }

    public boolean isActive()         { return isActive; }

    public boolean isBanned()         { return isBanned; }

    public MasterProfile getProfile() { return profile; }

    public void setProfile(MasterProfile profile) { this.profile = profile; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
