package api.dev.application.master.usecase.command;

import api.dev.domain.master.repository.MasterRepository;
import api.dev.domain.shared.exception.EntityNotFoundException;
import api.dev.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteMasterUseCase {

    private final MasterRepository masterRepository;
    private final UserRepository userRepository;

    public DeleteMasterUseCase(MasterRepository masterRepository,
                                UserRepository userRepository) {
        this.masterRepository = masterRepository;
        this.userRepository   = userRepository;
    }

    public void execute(DeleteMasterCommand command) {
        var master = masterRepository.findById(command.masterId())
                .orElseThrow(() -> new EntityNotFoundException("Master not found: " + command.masterId()));
        master.getUser().softDelete();
        userRepository.save(master.getUser());
    }
}
