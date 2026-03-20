package api.dev.application.master.usecase.command;

public record CreateMasterCommand(
        String email,
        String password,
        String nickname
) {}
