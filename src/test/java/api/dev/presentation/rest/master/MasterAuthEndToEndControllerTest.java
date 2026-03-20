package api.dev.presentation.rest.master;

import api.dev.BaseIntegrationTest;
import api.dev.infrastructure.security.jwt.JwtProperties;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("[E2E] POST /api/v1/master/auth/login")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MasterAuthEndToEndControllerTest extends BaseIntegrationTest {

    @Autowired private JwtProperties jwtProperties;

    static String cachedToken;

    @AfterAll
    void tearDown() {
        cleanDatabase();
    }

    @Test
    @Order(1)
    @DisplayName("1. DB connection is alive")
    void dbConnection_isAlive() {
        Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
        Assertions.assertEquals(1, result);
    }

    @Test
    @Order(2)
    @DisplayName("2. returns 401 when email does not exist")
    void login_unknownEmail_returns401() throws Exception {
        mockMvc.perform(post("/api/v1/master/auth/login")
                        .contentType(Objects.requireNonNull(APPLICATION_JSON))
                        .content("""
                                {
                                    "email": "nobody@test.com",
                                    "password": "Pass12B4?"
                                }
                                """))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("authentication_failed"));
    }

    @Test
    @Order(3)
    @DisplayName("3. returns 401 when password is wrong")
    void login_wrongPassword_returns401() throws Exception {
        mockMvc.perform(post("/api/v1/master/auth/login")
                        .contentType(Objects.requireNonNull(APPLICATION_JSON))
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
    @Order(4)
    @DisplayName("4. returns 200 with token when credentials are valid")
    void login_validCredentials_returnsToken() throws Exception {
        var result = mockMvc.perform(post("/api/v1/master/auth/login")
                        .contentType(Objects.requireNonNull(APPLICATION_JSON))
                        .content("""
                                {
                                    "email": "master@webmaster.com",
                                    "password": "Pass12B4?"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.expires_in").value(jwtProperties.getTtl()))
                .andExpect(jsonPath("$.role").value("MASTER"))
                .andReturn();

        String body = result.getResponse().getContentAsString();
        cachedToken = body.replaceAll(".*\"token\":\"([^\"]+)\".*", "$1");
        Assertions.assertNotNull(cachedToken);
    }
}
