package api.dev.application.master.usecase.query;

import api.dev.application.master.dto.MasterAccessLogDto;
import api.dev.application.master.mapper.MasterDtoMapper;
import api.dev.domain.master.repository.MasterAccessLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetMasterAccessLogsByUserIdUseCase {

    private final MasterAccessLogRepository accessLogRepository;
    private final MasterDtoMapper mapper;

    public GetMasterAccessLogsByUserIdUseCase(MasterAccessLogRepository accessLogRepository,
                                               MasterDtoMapper mapper) {
        this.accessLogRepository = accessLogRepository;
        this.mapper              = mapper;
    }

    public List<MasterAccessLogDto> execute(GetMasterAccessLogsByUserIdQuery query) {
        return accessLogRepository.findAllByUserId(query.userId())
                .stream()
                .map(mapper::toDto)
                .toList();
    }
}
