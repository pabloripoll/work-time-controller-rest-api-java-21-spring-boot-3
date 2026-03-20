package api.dev.application.master;

import api.dev.application.master.mapper.MasterDtoMapper;
import api.dev.application.master.usecase.command.CreateMasterCommand;
import api.dev.application.master.usecase.command.CreateMasterUseCase;
import api.dev.domain.master.model.entity.Master;
import api.dev.domain.master.repository.MasterRepository;
import api.dev.domain.shared.exception.ValidationException;
import api.dev.domain.shared.valueobject.Email;
import api.dev.domain.user.model.entity.User;
import api.dev.domain.user.model.entity.UserRole;
import api.dev.domain.user.repository.UserRepository;
import api.dev.domain.user.service.PasswordHashingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// JUnit + Mockito

@ActiveProfiles("test")
@Transactional
@ExtendWith(MockitoExtension.class)
@DisplayName("CreateMasterUseCase")
class CreateMasterUseCaseTest {

    @Mock private UserRepository userRepository;
    @Mock private MasterRepository masterRepository;
    @Mock private PasswordHashingService passwordHasher;
    @Mock private MasterDtoMapper mapper;

    @InjectMocks
    private CreateMasterUseCase useCase;

    private User savedUser;
    private Master savedMaster;

    @BeforeEach
    void setUp() {
        savedUser = new User(1L, new Email("master@test.com"), "hashed",
                UserRole.MASTER, 1L, LocalDateTime.now(), LocalDateTime.now(), null);

        savedMaster = Master.create(savedUser);
        savedMaster.setId(1L);
    }

    @Test
    @DisplayName("execute() creates user, master and profile when email is new")
    void execute_createsSuccessfully() {
        when(userRepository.findByEmail(any(Email.class))).thenReturn(Optional.empty());
        when(passwordHasher.hashPassword("Password1!")).thenReturn("hashed");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(masterRepository.save(any(Master.class))).thenReturn(savedMaster);
        when(mapper.toDto(any(Master.class))).thenReturn(null); // DTO shape not under test here

        useCase.execute(new CreateMasterCommand("master@test.com", "Password1!", "pablo", 1L));

        verify(userRepository).save(any(User.class));
        verify(masterRepository).save(any(Master.class));
        verify(passwordHasher).hashPassword("Password1!");
    }

    @Test
    @DisplayName("execute() throws ValidationException when email already exists")
    void execute_throwsWhenEmailExists() {
        when(userRepository.findByEmail(any(Email.class))).thenReturn(Optional.of(savedUser));

        assertThatThrownBy(() ->
                useCase.execute(new CreateMasterCommand("master@test.com", "Password1!", "pablo", 1L))
        )
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Email already in use");

        verify(userRepository, never()).save(any());
        verify(masterRepository, never()).save(any());
    }
}
