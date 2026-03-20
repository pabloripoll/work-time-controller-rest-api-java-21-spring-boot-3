package api.dev.domain.master;

import api.dev.domain.master.model.entity.Master;
import api.dev.domain.shared.valueobject.Email;
import api.dev.domain.user.model.entity.User;
import api.dev.domain.user.model.entity.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Transactional
@DisplayName("Master domain entity")
class MasterTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(
            1L,
            new Email("master@test.com"),
            "hashed",
            UserRole.MASTER,
            LocalDateTime.now(),
            LocalDateTime.now(),
            null);
    }

    @Test
    @DisplayName("create() sets isActive=false and isBanned=false by default")
    void create_defaultState() {
        Master master = Master.create(user);

        assertThat(master.isActive()).isFalse();
        assertThat(master.isBanned()).isFalse();
        assertThat(master.getId()).isNull();       // DB assigns ID — not set yet
        assertThat(master.getUser()).isEqualTo(user);
    }

    @Test
    @DisplayName("activate() sets isActive=true and updates updatedAt")
    void activate_setsActiveTrue() {
        Master master = Master.create(user);
        LocalDateTime before = LocalDateTime.now();

        master.activate();

        assertThat(master.isActive()).isTrue();
        assertThat(master.getUpdatedAt()).isAfterOrEqualTo(before);
    }

    @Test
    @DisplayName("deactivate() sets isActive=false")
    void deactivate_setsActiveFalse() {
        Master master = Master.create(user);
        master.activate();

        master.deactivate();

        assertThat(master.isActive()).isFalse();
    }

    @Test
    @DisplayName("ban() sets isBanned=true")
    void ban_setsBannedTrue() {
        Master master = Master.create(user);

        master.ban();

        assertThat(master.isBanned()).isTrue();
    }

    @Test
    @DisplayName("unban() sets isBanned=false")
    void unban_setsBannedFalse() {
        Master master = Master.create(user);
        master.ban();

        master.unban();

        assertThat(master.isBanned()).isFalse();
    }
}
