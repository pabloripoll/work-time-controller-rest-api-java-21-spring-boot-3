package api.dev.application.master.usecase.query;

import api.dev.application.master.dto.MasterDto;
import api.dev.application.master.mapper.MasterDtoMapper;
import api.dev.application.shared.dto.PaginatedDto;
import api.dev.domain.master.repository.MasterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllMastersUseCase {

    private final MasterRepository masterRepository;
    private final MasterDtoMapper mapper;

    public GetAllMastersUseCase(MasterRepository masterRepository, MasterDtoMapper mapper) {
        this.masterRepository = masterRepository;
        this.mapper           = mapper;
    }

    public PaginatedDto<MasterDto> execute(GetAllMastersQuery query) {
        var page = masterRepository.findAll(query.page(), query.perPage());
        List<MasterDto> data = page.getContent().stream().map(mapper::toDto).toList();
        return new PaginatedDto<>(data, query.page(), query.perPage(), page.getTotalElements());
    }
}
