package api.dev.application.master.usecase.query;

import api.dev.application.master.dto.MasterDto;
import api.dev.application.master.mapper.MasterDtoMapper;
import api.dev.domain.master.repository.MasterRepository;
import api.dev.domain.shared.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GetMasterByUserIdUseCase {

    private final MasterRepository masterRepository;
    private final MasterDtoMapper mapper;

    public GetMasterByUserIdUseCase(MasterRepository masterRepository, MasterDtoMapper mapper) {
        this.masterRepository = masterRepository;
        this.mapper           = mapper;
    }

    public MasterDto execute(GetMasterByUserIdQuery query) {
        return masterRepository.findByUserId(query.userId())
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Master not found for userId: " + query.userId()));
    }
}
