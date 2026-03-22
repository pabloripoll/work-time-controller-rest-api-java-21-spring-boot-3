package api.dev.application.master.dto;

import java.time.LocalDateTime;

public record MasterDto(
        Long id,
        Long userId,
        boolean isActive,
        boolean isBanned,
        boolean isSupermaster,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        MasterProfileDto profile
) {}
