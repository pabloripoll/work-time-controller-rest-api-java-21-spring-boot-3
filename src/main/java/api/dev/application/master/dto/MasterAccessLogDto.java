package api.dev.application.master.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record MasterAccessLogDto(
        Long id,
        Long userId,
        String token,
        LocalDateTime expiresAt,
        String ipAddress,
        String userAgent,
        Map<String, Object> payload,
        boolean isTerminated,
        boolean isExpired,
        int refreshCount,
        int requestsCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
