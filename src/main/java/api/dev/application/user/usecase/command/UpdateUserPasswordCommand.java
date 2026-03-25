package api.dev.application.user.usecase.command;

public record UpdateUserPasswordCommand(
        Long userId,
        String password
) {}
