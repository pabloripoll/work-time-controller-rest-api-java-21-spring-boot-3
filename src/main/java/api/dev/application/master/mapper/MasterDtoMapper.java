package api.dev.application.master.mapper;

import api.dev.application.master.dto.MasterAccessLogDto;
import api.dev.application.master.dto.MasterDto;
import api.dev.application.master.dto.MasterProfileDto;
import api.dev.domain.master.model.entity.Master;
import api.dev.domain.master.model.entity.MasterAccessLog;
import api.dev.domain.master.model.entity.MasterProfile;
import org.springframework.stereotype.Component;

@Component
public class MasterDtoMapper {

    public MasterDto toDto(Master master) {
        MasterProfileDto profileDto = master.getProfile() != null
                ? toDto(master.getProfile())
                : null;

        return new MasterDto(
                master.getId(),
                master.getUser().getId(),
                master.isActive(),
                master.isBanned(),
                master.getCreatedAt(),
                master.getUpdatedAt(),
                profileDto
        );
    }

    public MasterProfileDto toDto(MasterProfile profile) {
        return new MasterProfileDto(
                profile.getId(),
                profile.getNickname(),
                profile.getAvatar()
        );
    }

    public MasterAccessLogDto toDto(MasterAccessLog log) {
        return new MasterAccessLogDto(
                log.getId(),
                log.getUser().getId(),
                log.getToken(),
                log.getExpiresAt(),
                log.getIpAddress(),
                log.getUserAgent(),
                log.getPayload(),
                log.isTerminated(),
                log.isExpired(),
                log.getRefreshCount(),
                log.getRequestsCount(),
                log.getCreatedAt(),
                log.getUpdatedAt()
        );
    }
}
