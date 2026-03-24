package api.dev.application.master.usecase.command;

import api.dev.domain.master.repository.MasterRepository;
import api.dev.domain.shared.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UploadMasterAvatarUseCase {

    private final MasterRepository masterRepository;

    public UploadMasterAvatarUseCase(MasterRepository masterRepository) {
        this.masterRepository = masterRepository;
    }

    public String execute(UploadMasterAvatarCommand command) {
        var master = masterRepository.findById(command.masterId())
                .orElseThrow(() -> new EntityNotFoundException("Master not found with id: " + command.masterId()));

        var profile = master.getProfile();
        if (profile == null) {
            throw new EntityNotFoundException("Profile not found for master: " + command.masterId());
        }

        profile.updateAvatar(command.avatar());
        masterRepository.save(master);

        return profile.getAvatar();
    }
}
