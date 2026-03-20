package api.dev.infrastructure.seeder;

import api.dev.domain.master.model.entity.Master;
import api.dev.domain.master.model.entity.MasterProfile;
import api.dev.domain.master.repository.MasterRepository;
import api.dev.domain.shared.valueobject.Email;
import api.dev.domain.user.model.entity.User;
import api.dev.domain.user.repository.UserRepository;
import api.dev.domain.user.service.PasswordHashingService;
import net.datafaker.Faker;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.Optional;

@Component
@Profile({"dev", "local", "test"})
public class UserMasterSeeder {

    private final UserRepository userRepository;
    private final MasterRepository masterRepository;
    private final PasswordHashingService passwordHasher;
    private final Faker faker;

    public UserMasterSeeder(UserRepository userRepository,
                            MasterRepository masterRepository,
                            PasswordHashingService passwordHasher) {
        this.userRepository   = userRepository;
        this.masterRepository = masterRepository;
        this.passwordHasher   = passwordHasher;
        this.faker            = new Faker();
    }

    public void seed() {
        String nickname           = faker.name().firstName();
        String normalizedNickname = sanitizeForEmail(nickname);

        // Email is a record — use new Email(string), not Email.fromString()
        Email email = new Email("master@webmaster.com");

        Optional<User> existingUser = userRepository.findByEmail(email);

        User user;
        if (existingUser.isEmpty()) {
            // User.createMaster signature: (Long id, Email email, String password, Long createdByUserId)
            // — no nickname parameter in this factory method
            user = User.createMaster(null, email, "temp");
            user.updatePassword(passwordHasher.hashPassword("Pass12B4?"));
            user = userRepository.save(user);
        } else {
            user = existingUser.get();
        }

        Optional<Master> existingMaster = masterRepository.findByUserId(user.getId());

        if (existingMaster.isEmpty()) {
            // Master.create signature: (User user) — not (Long, boolean, boolean)
            Master master = Master.create(user);
            master.setProfile(MasterProfile.create(normalizedNickname));
            masterRepository.save(master);
        }
    }

    private String sanitizeForEmail(String input) {
        if (input == null) return "";
        String normalized     = Normalizer.normalize(input, Normalizer.Form.NFD);
        String withoutAccents = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        return withoutAccents.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
    }
}
