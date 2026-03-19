package api.dev.domain.user.service;

public interface PasswordHashingService {
    String hashPassword(String rawPassword);
    boolean matches(String rawPassword, String hashedPassword);
}
