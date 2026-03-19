package api.dev.application.master.usecase.command;

import api.dev.domain.master.repository.MasterRepository;
import api.dev.domain.shared.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UnbanMasterUseCase {

    private final MasterRepository masterRepository;

    public UnbanMasterUseCase(MasterRepository masterRepository) {
        this.masterRepository = masterRepository;
    }

    public void execute(UnbanMasterCommand command) {
        var master = masterRepository.findById(command.masterId())
                .orElseThrow(() -> new EntityNotFoundException("Master not found: " + command.masterId()));
        master.unban();
        masterRepository.save(master);
    }
}
