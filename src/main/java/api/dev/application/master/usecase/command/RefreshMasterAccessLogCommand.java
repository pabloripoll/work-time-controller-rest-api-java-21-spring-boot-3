package api.dev.application.master.usecase.command;

import java.time.LocalDateTime;

public record RefreshMasterAccessLogCommand(
        String currentToken,
        String newToken,
        LocalDateTime newExpiresAt
) {}
