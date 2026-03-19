package api.dev.application.master.usecase.query;

import api.dev.application.master.dto.MasterAccessLogDto;
import api.dev.application.master.mapper.MasterDtoMapper;
import api.dev.domain.master.repository.MasterAccessLogRepository;
import api.dev.domain.shared.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GetMasterAccessLogByTokenUseCase {

    private final MasterAccessLogRepository accessLogRepository;
    private final MasterDtoMapper mapper;

    public GetMasterAccessLogByTokenUseCase(MasterAccessLogRepository accessLogRepository,
                                             MasterDtoMapper mapper) {
        this.accessLogRepository = accessLogRepository;
        this.mapper              = mapper;
    }

    public MasterAccessLogDto execute(GetMasterAccessLogByTokenQuery query) {
        return accessLogRepository.findByToken(query.token())
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Access log not found for token"));
    }
}
