package api.dev.infrastructure.security.service;

import api.dev.domain.user.service.PasswordHashingService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BcryptPasswordHashingService implements PasswordHashingService {

    private final BCryptPasswordEncoder encoder;

    // Injected from SecurityBeansConfig
    public BcryptPasswordHashingService(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public String hashPassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String hashedPassword) {
        return encoder.matches(rawPassword, hashedPassword);
    }
}
