package api.dev.application.master.usecase.command;

import api.dev.application.master.dto.MasterAccessLogDto;
import api.dev.application.master.mapper.MasterDtoMapper;
import api.dev.domain.master.model.entity.MasterAccessLog;
import api.dev.domain.master.repository.MasterAccessLogRepository;
import api.dev.domain.shared.exception.EntityNotFoundException;
import api.dev.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateMasterAccessLogUseCase {

    private final MasterAccessLogRepository accessLogRepository;
    private final UserRepository userRepository;
    private final MasterDtoMapper mapper;

    public CreateMasterAccessLogUseCase(MasterAccessLogRepository accessLogRepository,
                                         UserRepository userRepository,
                                         MasterDtoMapper mapper) {
        this.accessLogRepository = accessLogRepository;
        this.userRepository      = userRepository;
        this.mapper              = mapper;
    }

    public MasterAccessLogDto execute(CreateMasterAccessLogCommand command) {
        var user = userRepository.findById(command.userId())
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + command.userId()));
        var log = MasterAccessLog.create(user, command.token(), command.expiresAt(),
                command.ipAddress(), command.userAgent(), command.payload());
        return mapper.toDto(accessLogRepository.save(log));
    }
}
