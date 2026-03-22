package api.dev.presentation.rest.master;

import api.dev.application.master.dto.MasterDto;
import api.dev.application.master.dto.MasterProfileDto;
import api.dev.application.master.usecase.command.UpdateMasterProfileUseCase;
import api.dev.application.master.usecase.query.GetMasterByUserIdUseCase;
import api.dev.domain.shared.valueobject.Email;
import api.dev.domain.user.model.entity.User;
import api.dev.domain.user.model.entity.UserRole;
import api.dev.infrastructure.security.jwt.JwtService;
import api.dev.infrastructure.security.service.UserDetailsServiceImpl;
import api.dev.infrastructure.security.userdetails.AuthenticatedUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("[FUNC] MasterAccountController")
class MasterAccountControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private JwtService jwtService;

    @SpyBean  private UserDetailsServiceImpl userDetailsService;  // ← SpyBean wraps real bean
    @MockBean private GetMasterByUserIdUseCase getMasterByUserIdUseCase;
    @MockBean private UpdateMasterProfileUseCase updateProfileUseCase;

    private String validMasterToken;
    private String validEmployeeToken;

    private MasterDto masterDto;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();

        // --- Master user ---
        var masterDomainUser = new User(
            1L,
            new Email("master@test.com"),
            "hashed",
            UserRole.MASTER,
            now, now, null
        );
        var masterAuth = new AuthenticatedUser(masterDomainUser);
        validMasterToken = jwtService.generateToken("master@test.com", "MASTER");
        doReturn(masterAuth).when(userDetailsService).loadUserByUsername("master@test.com"); // ← doReturn

        // --- Employee user (for 403 test) ---
        var employeeDomainUser = new User(
            2L,
            new Email("emp@test.com"),
            "hashed",
            UserRole.EMPLOYEE,
            now, now, null
        );
        var employeeAuth = new AuthenticatedUser(employeeDomainUser);
        validEmployeeToken = jwtService.generateToken("emp@test.com", "EMPLOYEE");
        doReturn(employeeAuth).when(userDetailsService).loadUserByUsername("emp@test.com"); // ← doReturn

        // --- Stub DTO ---
        var profileDto = new MasterProfileDto(1L, "other", null);
        masterDto = new MasterDto(1L, 1L, true, false, false, now, now, profileDto);
    }

    @Test
    @DisplayName("GET /account/profile returns 200 with profile data when authenticated as MASTER")
    void getProfile_authenticated_returns200() throws Exception {
        when(getMasterByUserIdUseCase.execute(any())).thenReturn(masterDto);

        mockMvc.perform(get("/api/v1/master/account/profile")
                        .header("Authorization", "Bearer " + validMasterToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.nickname").value("other"));
    }

    @Test
    @DisplayName("GET /account/profile returns 401 when no token provided")
    void getProfile_noToken_returns401() throws Exception {
        mockMvc.perform(get("/api/v1/master/account/profile"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("unauthenticated"));
    }

    @Test
    @DisplayName("GET /account/profile returns 403 when authenticated as EMPLOYEE")
    void getProfile_wrongRole_returns403() throws Exception {
        mockMvc.perform(get("/api/v1/master/account/profile")
                        .header("Authorization", "Bearer " + validEmployeeToken))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.error").value("access_denied"));
    }

    @Test
    @DisplayName("PATCH /account/settings/profile returns 200 when authenticated as MASTER")
    void updateProfile_authenticated_returns200() throws Exception {
        when(getMasterByUserIdUseCase.execute(any())).thenReturn(masterDto);

        mockMvc.perform(patch("/api/v1/master/account/settings/profile")
                        .header("Authorization", "Bearer " + validMasterToken)
                        .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
                        .content("""
                                { "nickname": "newname" }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"));
    }
}
