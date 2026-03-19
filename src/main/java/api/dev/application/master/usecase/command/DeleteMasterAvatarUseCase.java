package api.dev.application.master.usecase.command;

import api.dev.domain.master.repository.MasterRepository;
import api.dev.domain.shared.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DeleteMasterAvatarUseCase {

    private final MasterRepository masterRepository;

    public DeleteMasterAvatarUseCase(MasterRepository masterRepository) {
        this.masterRepository = masterRepository;
    }

    public void execute(DeleteMasterAvatarCommand command) {
        var master = masterRepository.findById(command.masterId())
                .orElseThrow(() -> new EntityNotFoundException("Master not found: " + command.masterId()));
        var profile = master.getProfile();
        if (profile == null) {
            throw new EntityNotFoundException("Profile not found for master: " + command.masterId());
        }
        profile.removeAvatar();
        masterRepository.save(master);
    }
}
