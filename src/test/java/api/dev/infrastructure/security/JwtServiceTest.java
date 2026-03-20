package api.dev.infrastructure.security;

import api.dev.infrastructure.security.jwt.JwtProperties;
import api.dev.infrastructure.security.jwt.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Transactional
@DisplayName("JwtService")
class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        JwtProperties props = new JwtProperties();
        // Must be at least 256 bits (32 chars) for HS256
        props.setSecret("test-secret-key-must-be-32-chars!");
        props.setTtl(3600L);
        jwtService = new JwtService(props);
    }

    @Test
    @DisplayName("generateToken() produces a non-blank token")
    void generateToken_producesToken() {
        String token = jwtService.generateToken("master@test.com", "MASTER");

        assertThat(token).isNotBlank();
    }

    @Test
    @DisplayName("extractEmail() returns the email used to generate the token")
    void extractEmail_returnsCorrectEmail() {
        String token = jwtService.generateToken("master@test.com", "MASTER");

        assertThat(jwtService.extractEmail(token)).isEqualTo("master@test.com");
    }

    @Test
    @DisplayName("extractRole() returns the role used to generate the token")
    void extractRole_returnsCorrectRole() {
        String token = jwtService.generateToken("master@test.com", "MASTER");

        assertThat(jwtService.extractRole(token)).isEqualTo("MASTER");
    }

    @Test
    @DisplayName("extractExpiry() returns a future datetime")
    void extractExpiry_returnsFutureDate() {
        String token = jwtService.generateToken("master@test.com", "MASTER");
        LocalDateTime expiry = jwtService.extractExpiry(token);

        assertThat(expiry).isAfter(LocalDateTime.now());
    }

    @Test
    @DisplayName("isTokenValid() returns true for a fresh token")
    void isTokenValid_trueForFreshToken() {
        String token = jwtService.generateToken("master@test.com", "MASTER");

        assertThat(jwtService.isTokenValid(token)).isTrue();
    }

    @Test
    @DisplayName("isTokenValid() returns false for a tampered token")
    void isTokenValid_falseForTamperedToken() {
        assertThat(jwtService.isTokenValid("not.a.valid.token")).isFalse();
    }

    @Test
    @DisplayName("isTokenValid() returns false for an expired token")
    void isTokenValid_falseForExpiredToken() {
        // TTL of -1 second = already expired at generation time
        JwtProperties expiredProps = new JwtProperties();
        expiredProps.setSecret("test-secret-key-must-be-32-chars!");
        expiredProps.setTtl(-1L);
        JwtService expiredService = new JwtService(expiredProps);

        String token = expiredService.generateToken("master@test.com", "MASTER");

        assertThat(jwtService.isTokenValid(token)).isFalse();
    }
}
