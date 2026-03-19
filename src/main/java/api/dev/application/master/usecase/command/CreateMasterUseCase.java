package api.dev.application.master.usecase.command;

import api.dev.application.master.dto.MasterDto;
import api.dev.application.master.mapper.MasterDtoMapper;
import api.dev.domain.master.model.entity.Master;
import api.dev.domain.master.model.entity.MasterProfile;
import api.dev.domain.master.repository.MasterRepository;
import api.dev.domain.shared.exception.ValidationException;
import api.dev.domain.shared.valueobject.Email;
import api.dev.domain.user.model.entity.User;
import api.dev.domain.user.repository.UserRepository;
import api.dev.domain.user.service.PasswordHashingService;
import org.springframework.stereotype.Service;

@Service
public class CreateMasterUseCase {

    private final UserRepository userRepository;
    private final MasterRepository masterRepository;
    private final PasswordHashingService passwordHasher;
    private final MasterDtoMapper mapper;

    public CreateMasterUseCase(UserRepository userRepository,
                                MasterRepository masterRepository,
                                PasswordHashingService passwordHasher,
                                MasterDtoMapper mapper) {
        this.userRepository   = userRepository;
        this.masterRepository = masterRepository;
        this.passwordHasher   = passwordHasher;
        this.mapper           = mapper;
    }

    public MasterDto execute(CreateMasterCommand command) {
        Email email = Email.fromString(command.email());

        if (userRepository.findByEmail(email).isPresent()) {
            throw new ValidationException("Email already in use: " + command.email());
        }

        User user = User.createMaster(
                null,
                email,
                passwordHasher.hashPassword(command.password()),
                command.nickname(),
                command.createdByUserId()
        );
        user = userRepository.save(user);

        Master master = Master.create(user);
        master.setProfile(MasterProfile.create(command.nickname()));
        master = masterRepository.save(master);

        return mapper.toDto(master);
    }
}
