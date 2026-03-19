package api.dev.application.master.usecase.command;

import api.dev.domain.master.repository.MasterRepository;
import api.dev.domain.shared.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ActivateMasterUseCase {

    private final MasterRepository masterRepository;

    public ActivateMasterUseCase(MasterRepository masterRepository) {
        this.masterRepository = masterRepository;
    }

    public void execute(ActivateMasterCommand command) {
        var master = masterRepository.findById(command.masterId())
                .orElseThrow(() -> new EntityNotFoundException("Master not found: " + command.masterId()));
        master.activate();
        masterRepository.save(master);
    }
}
