package api.dev.application.master.usecase.command;

import java.time.LocalDateTime;
import java.util.Map;

public record CreateMasterAccessLogCommand(
        Long userId,
        String token,
        LocalDateTime expiresAt,
        String ipAddress,
        String userAgent,
        Map<String, Object> payload
) {}
