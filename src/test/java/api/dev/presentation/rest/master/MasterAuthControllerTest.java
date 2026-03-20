package api.dev.presentation.rest.master;

import api.dev.domain.master.repository.MasterAccessLogRepository;
import api.dev.domain.shared.valueobject.Email;
import api.dev.domain.user.model.entity.User;
import api.dev.domain.user.model.entity.UserRole;
import api.dev.infrastructure.security.jwt.JwtProperties;
import api.dev.infrastructure.security.service.UserDetailsServiceImpl;
import api.dev.infrastructure.security.userdetails.AuthenticatedUser;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("[Unit] POST /api/v1/master/auth/login")
class MasterAuthControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private JwtProperties jwtProperties;

    // --- mock every bean that touches the DB ---
    @MockBean private UserDetailsServiceImpl userDetailsService;
    @MockBean private MasterAccessLogRepository masterAccessLogRepository; // ← this was missing

    private AuthenticatedUser authenticatedMaster;

    private static final String RAW_PASSWORD    = "Pass12B4?";
    private static final String HASHED_PASSWORD = new BCryptPasswordEncoder().encode(RAW_PASSWORD);

    @BeforeEach
    void setUp() {
        var domainUser = new User(
                1L,
                new Email("master@webmaster.com"),
                HASHED_PASSWORD,
                UserRole.MASTER,
                1L,
                LocalDateTime.now(),
                LocalDateTime.now(),
                null);
        authenticatedMaster = new AuthenticatedUser(domainUser);

        // stub the access log save so AuthSuccessHandler doesn't hit the DB
        when(masterAccessLogRepository.save(any())).thenAnswer(i -> i.getArgument(0));
    }

    @Test
    @DisplayName("returns 200 with token when credentials are valid")
    void login_validCredentials_returnsToken() throws Exception {
        when(userDetailsService.loadUserByUsername("master@webmaster.com"))
                .thenReturn(authenticatedMaster);

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
        when(userDetailsService.loadUserByUsername("master@webmaster.com"))
                .thenReturn(authenticatedMaster);

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
        when(userDetailsService.loadUserByUsername(any()))
                .thenThrow(new org.springframework.security.core.userdetails
                        .UsernameNotFoundException("not found"));

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
