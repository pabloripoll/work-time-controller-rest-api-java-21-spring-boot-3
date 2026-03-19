package api.dev.domain.master.model.entity;

import java.time.LocalDateTime;

public class MasterProfile {

    private Long id;
    private String nickname;
    private String avatar;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ------------------------------------------------------------------ //
    // Private constructor — force use of factory methods (DDD)
    // ------------------------------------------------------------------ //
    private MasterProfile() {}

    /**
     * Factory: brand-new profile (created during Master registration).
     * ID is null — DB will assign it.
     * avatar is null — can be set later.
     */
    public static MasterProfile create(String nickname) {
        if (nickname == null || nickname.isBlank()) {
            throw new IllegalArgumentException("Nickname cannot be blank");
        }
        MasterProfile profile = new MasterProfile();
        profile.nickname  = nickname.trim();
        profile.createdAt = LocalDateTime.now();
        profile.updatedAt = LocalDateTime.now();
        return profile;
    }

    /**
     * Factory: reconstitute from persistence.
     * All fields are known, including DB-assigned ID.
     */
    public static MasterProfile reconstitute(Long id, String nickname, String avatar,
                                              LocalDateTime createdAt, LocalDateTime updatedAt) {
        MasterProfile profile = new MasterProfile();
        profile.id        = id;
        profile.nickname  = nickname;
        profile.avatar    = avatar;
        profile.createdAt = createdAt;
        profile.updatedAt = updatedAt;
        return profile;
    }

    // ------------------------------------------------------------------ //
    // Business behaviour
    // ------------------------------------------------------------------ //
    public void updateNickname(String nickname) {
        if (nickname == null || nickname.isBlank()) {
            throw new IllegalArgumentException("Nickname cannot be blank");
        }
        this.nickname  = nickname.trim();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateAvatar(String avatar) {
        this.avatar    = avatar;
        this.updatedAt = LocalDateTime.now();
    }

    public void removeAvatar() {
        this.avatar    = null;
        this.updatedAt = LocalDateTime.now();
    }

    // ------------------------------------------------------------------ //
    // Getters — no raw setters on business state (use methods above)
    // ------------------------------------------------------------------ //
    public Long getId()               { return id; }
    public void setId(Long id)        { this.id = id; } // only for JPA mapper

    public String getNickname()         { return nickname; }
    public String getAvatar()           { return avatar; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
