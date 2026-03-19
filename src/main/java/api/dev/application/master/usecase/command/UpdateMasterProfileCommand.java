package api.dev.application.master.usecase.command;

public record UpdateMasterProfileCommand(
        Long masterId,
        String nickname,
        String avatar
) {}
