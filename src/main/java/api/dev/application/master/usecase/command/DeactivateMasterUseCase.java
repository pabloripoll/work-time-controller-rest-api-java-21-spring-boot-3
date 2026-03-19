package api.dev.application.master.usecase.command;

import api.dev.domain.master.repository.MasterRepository;
import api.dev.domain.shared.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DeactivateMasterUseCase {

    private final MasterRepository masterRepository;

    public DeactivateMasterUseCase(MasterRepository masterRepository) {
        this.masterRepository = masterRepository;
    }

    public void execute(DeactivateMasterCommand command) {
        var master = masterRepository.findById(command.masterId())
                .orElseThrow(() -> new EntityNotFoundException("Master not found: " + command.masterId()));
        master.deactivate();
        masterRepository.save(master);
    }
}
