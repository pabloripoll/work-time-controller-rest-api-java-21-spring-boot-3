package api.dev.application.master.usecase.command;

import api.dev.domain.master.repository.MasterAccessLogRepository;
import api.dev.domain.shared.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TerminateMasterAccessLogUseCase {

    private final MasterAccessLogRepository accessLogRepository;

    public TerminateMasterAccessLogUseCase(MasterAccessLogRepository accessLogRepository) {
        this.accessLogRepository = accessLogRepository;
    }

    public void execute(TerminateMasterAccessLogCommand command) {
        var log = accessLogRepository.findByToken(command.token())
                .orElseThrow(() -> new EntityNotFoundException("Access log not found for token"));
        log.terminate();
        accessLogRepository.save(log);
    }
}
