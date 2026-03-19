package api.dev.application.master.usecase.query;

import api.dev.application.master.dto.MasterDto;
import api.dev.application.master.mapper.MasterDtoMapper;
import api.dev.domain.master.repository.MasterRepository;
import api.dev.domain.shared.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GetMasterByIdUseCase {

    private final MasterRepository masterRepository;
    private final MasterDtoMapper mapper;

    public GetMasterByIdUseCase(MasterRepository masterRepository, MasterDtoMapper mapper) {
        this.masterRepository = masterRepository;
        this.mapper = mapper;
    }

    public MasterDto execute(GetMasterByIdQuery query) {
        return masterRepository.findById(query.masterId())
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Master not found with id: " + query.masterId()));
    }
}
