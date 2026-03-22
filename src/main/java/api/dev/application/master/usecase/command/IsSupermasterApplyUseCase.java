package api.dev.application.master.usecase.command;

import api.dev.domain.master.repository.MasterRepository;
import api.dev.domain.shared.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class IsSupermasterApplyUseCase {

    private final MasterRepository masterRepository;

    public IsSupermasterApplyUseCase(MasterRepository masterRepository) {
        this.masterRepository = masterRepository;
    }

    public void execute(IsSupermasterApplyCommand command) {
        var master = masterRepository.findById(command.masterId())
                .orElseThrow(() -> new EntityNotFoundException("Master not found: " + command.masterId()));
        master.applyIsSupermaster();
        masterRepository.save(master);
    }
}
