package api.dev.application.master.usecase.command;

import api.dev.domain.master.repository.MasterRepository;
import api.dev.domain.shared.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UpdateMasterProfileUseCase {

    private final MasterRepository masterRepository;

    public UpdateMasterProfileUseCase(MasterRepository masterRepository) {
        this.masterRepository = masterRepository;
    }

    public void execute(UpdateMasterProfileCommand command) {
        var master = masterRepository.findById(command.masterId())
                .orElseThrow(() -> new EntityNotFoundException("Master not found with id: " + command.masterId()));

        var profile = master.getProfile();
        if (profile == null) {
            throw new EntityNotFoundException("Profile not found for master: " + command.masterId());
        }

        if (command.nickname() != null) {
            profile.updateNickname(command.nickname());
        }

        masterRepository.save(master);
    }
}
