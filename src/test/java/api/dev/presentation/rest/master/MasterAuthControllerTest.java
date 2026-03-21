package api.dev.presentation.rest.master;

import api.dev.infrastructure.persistence.master.MasterAccessLogJpaRepository;
import api.dev.infrastructure.persistence.user.UserJpaEntity;
import api.dev.infrastructure.persistence.user.UserJpaRepository;
import api.dev.domain.user.model.entity.UserRole;
import api.dev.infrastructure.security.jwt.JwtProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("[Unit] POST /api/v1/master/auth/login")
class MasterAuthControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private JwtProperties jwtProperties;

    @MockBean private UserJpaRepository userJpaRepository;
    @MockBean private MasterAccessLogJpaRepository masterAccessLogJpaRepository;

    private static final String RAW_PASSWORD    = "Pass12B4?";
    private static final String HASHED_PASSWORD = new BCryptPasswordEncoder().encode(RAW_PASSWORD);

    private UserJpaEntity userJpaEntity;

    @SuppressWarnings("null")
    @BeforeEach
    void setUp() {
        userJpaEntity = new UserJpaEntity();
        userJpaEntity.setId(1L);
        userJpaEntity.setEmail("master@webmaster.com");
        userJpaEntity.setPassword(HASHED_PASSWORD);
        userJpaEntity.setRole(UserRole.MASTER);
        userJpaEntity.setCreatedAt(LocalDateTime.now());
        userJpaEntity.setUpdatedAt(LocalDateTime.now());

        // MasterJpaMapper.toDomain(MasterAccessLogJpaEntity) calls findById to hydrate the User
        when(userJpaRepository.findById(anyLong()))
                .thenReturn(Optional.of(userJpaEntity));

        // AuthSuccessHandler calls masterAccessLogRepository.save()
        when(masterAccessLogJpaRepository.save(any()))
                .thenAnswer(i -> i.getArgument(0));
    }

    @Test
    @DisplayName("returns 200 with token when credentials are valid")
    void login_validCredentials_returnsToken() throws Exception {
        when(userJpaRepository.findByEmail("master@webmaster.com"))
                .thenReturn(Optional.of(userJpaEntity));

        mockMvc.perform(post("/api/v1/master/auth/login")
                        .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
                        .content(Objects.requireNonNull("""
                                {
                                    "email": "master@webmaster.com",
                                    "password": "%s"
                                }
                                """.formatted(RAW_PASSWORD))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.expires_in").value(jwtProperties.getTtl()))
                .andExpect(jsonPath("$.role").value("MASTER"));
    }

    @Test
    @DisplayName("returns 401 when password is wrong")
    void login_wrongPassword_returns401() throws Exception {
        when(userJpaRepository.findByEmail("master@webmaster.com"))
                .thenReturn(Optional.of(userJpaEntity));

        mockMvc.perform(post("/api/v1/master/auth/login")
                        .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
                        .content("""
                                {
                                    "email": "master@webmaster.com",
                                    "password": "WrongPassword!"
                                }
                                """))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("authentication_failed"));
    }

    @Test
    @DisplayName("returns 401 when email does not exist")
    void login_unknownEmail_returns401() throws Exception {
        when(userJpaRepository.findByEmail(anyString()))
                .thenReturn(Optional.empty());

        mockMvc.perform(post("/api/v1/master/auth/login")
                        .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
                        .content("""
                                {
                                    "email": "nobody@test.com",
                                    "password": "Pass12B4?"
                                }
                                """))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("authentication_failed"));
    }
}
