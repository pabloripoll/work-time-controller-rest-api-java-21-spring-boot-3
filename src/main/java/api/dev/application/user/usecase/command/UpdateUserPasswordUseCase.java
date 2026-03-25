package api.dev.application.user.usecase.command;

import api.dev.domain.user.repository.UserRepository;
import api.dev.domain.shared.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserPasswordUseCase {

    private final UserRepository userRepository;

    public UpdateUserPasswordUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(UpdateUserPasswordCommand command) {
        var user = userRepository.findById(command.userId())
                .orElseThrow(() -> new EntityNotFoundException("Master user not found with id: " + command.userId()));

        if (command.password() != null) {
            user.updatePassword(command.password());
        }

        userRepository.save(user);
    }
}
