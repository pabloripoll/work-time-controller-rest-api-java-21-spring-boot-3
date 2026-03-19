package api.dev.application.master.usecase.query;

import api.dev.application.master.dto.MasterProfileDto;
import api.dev.application.master.mapper.MasterDtoMapper;
import api.dev.domain.master.repository.MasterRepository;
import api.dev.domain.shared.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GetMasterProfileUseCase {

    private final MasterRepository masterRepository;
    private final MasterDtoMapper mapper;

    public GetMasterProfileUseCase(MasterRepository masterRepository, MasterDtoMapper mapper) {
        this.masterRepository = masterRepository;
        this.mapper           = mapper;
    }

    public MasterProfileDto execute(GetMasterProfileQuery query) {
        var master = masterRepository.findById(query.masterId())
                .orElseThrow(() -> new EntityNotFoundException("Master not found: " + query.masterId()));
        var profile = master.getProfile();
        if (profile == null) {
            throw new EntityNotFoundException("Profile not found for master: " + query.masterId());
        }
        return mapper.toDto(profile);
    }
}
