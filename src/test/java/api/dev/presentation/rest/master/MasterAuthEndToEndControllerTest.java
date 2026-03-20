package api.dev.presentation.rest.master;

import api.dev.infrastructure.security.jwt.JwtProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional          // ← rolls back every test — DB stays clean between runs
@DisplayName("POST /api/v1/master/auth/login")
class MasterAuthEndToEndControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private JwtProperties jwtProperties;

    // No @MockBean here — we want the REAL UserDetailsServiceImpl hitting the real DB
    // (remove the @MockBean that was there before)

    @Test
    @DisplayName("returns 200 with token when credentials are valid")
    @Sql("/sql/seed-master-user.sql")   // ← inserts test user before this test, rolled back after
    void login_validCredentials_returnsToken() throws Exception {
        mockMvc.perform(post("/api/v1/master/auth/login")
                        .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
                        .content("""
                                {
                                    "email": "master@webmaster.com",
                                    "password": "Pass12B4?"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.expires_in").value(jwtProperties.getTtl()))
                .andExpect(jsonPath("$.role").value("MASTER"));
    }

    @Test
    @DisplayName("returns 401 when password is wrong")
    @Sql("/sql/seed-master-user.sql")
    void login_wrongPassword_returns401() throws Exception {
        mockMvc.perform(post("/api/v1/master/auth/login")
                        .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
                        .content("""
                                {
                                    "email": "master@test.com",
                                    "password": "WrongPassword!"
                                }
                                """))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("authentication_failed"));
    }

    @Test
    @DisplayName("returns 401 when email does not exist")
    void login_unknownEmail_returns401() throws Exception {
        // No seed needed — DB is empty, user simply won't be found
        mockMvc.perform(post("/api/v1/master/auth/login")
                        .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
                        .content("""
                                {
                                    "email": "nobody@test.com",
                                    "password": "Password1!"
                                }
                                """))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("authentication_failed"));
    }
}
