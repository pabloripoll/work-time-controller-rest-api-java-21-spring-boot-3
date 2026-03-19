package api.dev.application.master.usecase.command;

import api.dev.application.master.dto.MasterAccessLogDto;
import api.dev.application.master.mapper.MasterDtoMapper;
import api.dev.domain.master.repository.MasterAccessLogRepository;
import api.dev.domain.shared.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RefreshMasterAccessLogUseCase {

    private final MasterAccessLogRepository accessLogRepository;
    private final MasterDtoMapper mapper;

    public RefreshMasterAccessLogUseCase(MasterAccessLogRepository accessLogRepository,
                                          MasterDtoMapper mapper) {
        this.accessLogRepository = accessLogRepository;
        this.mapper              = mapper;
    }

    public MasterAccessLogDto execute(RefreshMasterAccessLogCommand command) {
        var log = accessLogRepository.findByToken(command.currentToken())
                .orElseThrow(() -> new EntityNotFoundException("Access log not found for token"));
        log.refresh(command.newToken(), command.newExpiresAt());
        return mapper.toDto(accessLogRepository.save(log));
    }
}
