package api.dev.domain.master;

import api.dev.domain.master.model.entity.MasterProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@Transactional
@DisplayName("MasterProfile domain entity")
class MasterProfileTest {

    @Test
    @DisplayName("create() trims and stores nickname, avatar is null")
    void create_storesNickname() {
        MasterProfile profile = MasterProfile.create("  pablo  ");

        assertThat(profile.getNickname()).isEqualTo("pablo");
        assertThat(profile.getAvatar()).isNull();
        assertThat(profile.getId()).isNull();
    }

    @Test
    @DisplayName("create() throws when nickname is blank")
    void create_throwsOnBlankNickname() {
        assertThatThrownBy(() -> MasterProfile.create("  "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Nickname cannot be blank");
    }

    @Test
    @DisplayName("updateNickname() trims and updates")
    void updateNickname_updatesValue() {
        MasterProfile profile = MasterProfile.create("pablo");

        profile.updateNickname("  newname  ");

        assertThat(profile.getNickname()).isEqualTo("newname");
    }

    @Test
    @DisplayName("updateNickname() throws when blank")
    void updateNickname_throwsOnBlank() {
        MasterProfile profile = MasterProfile.create("pablo");

        assertThatThrownBy(() -> profile.updateNickname(""))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("updateAvatar() stores URL")
    void updateAvatar_storesUrl() {
        MasterProfile profile = MasterProfile.create("pablo");

        profile.updateAvatar("https://cdn.example.com/avatar.png");

        assertThat(profile.getAvatar()).isEqualTo("https://cdn.example.com/avatar.png");
    }

    @Test
    @DisplayName("removeAvatar() sets avatar to null")
    void removeAvatar_setsNull() {
        MasterProfile profile = MasterProfile.create("pablo");
        profile.updateAvatar("https://cdn.example.com/avatar.png");

        profile.removeAvatar();

        assertThat(profile.getAvatar()).isNull();
    }
}
