package api.dev.application.master.usecase.command;

public record UploadMasterAvatarCommand(
        Long masterId,
        String avatar
) {}
