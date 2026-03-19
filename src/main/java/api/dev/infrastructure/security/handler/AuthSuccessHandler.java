package api.dev.infrastructure.security.handler;

import api.dev.domain.master.model.entity.MasterAccessLog;
import api.dev.domain.master.repository.MasterAccessLogRepository;
import api.dev.infrastructure.security.jwt.JwtProperties;
import api.dev.infrastructure.security.jwt.JwtService;
import api.dev.infrastructure.security.userdetails.AuthenticatedUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final JwtProperties jwtProperties;
    private final MasterAccessLogRepository masterAccessLogRepository;
    private final ObjectMapper objectMapper;

    public AuthSuccessHandler(JwtService jwtService,
                               JwtProperties jwtProperties,
                               MasterAccessLogRepository masterAccessLogRepository,
                               ObjectMapper objectMapper) {
        this.jwtService                = jwtService;
        this.jwtProperties             = jwtProperties;
        this.masterAccessLogRepository = masterAccessLogRepository;
        this.objectMapper              = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Authentication authentication) throws IOException {

        AuthenticatedUser authUser = (AuthenticatedUser) authentication.getPrincipal();
        var domainUser = authUser.getDomainUser();

        String token    = jwtService.generateToken(domainUser.getEmail().value(), domainUser.getRole().name());
        LocalDateTime expiresAt = LocalDateTime.now().plusSeconds(jwtProperties.getTtl());

        // Record the session in the appropriate access log table based on role
        if (domainUser.isMaster()) {
            var log = MasterAccessLog.create(
                    domainUser,
                    token,
                    expiresAt,
                    request.getRemoteAddr(),
                    request.getHeader("User-Agent"),
                    null
            );
            masterAccessLogRepository.save(log);
        }
        // TODO: add adminAccessLogRepository, employeeAccessLogRepository when implemented

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        objectMapper.writeValue(response.getWriter(), Map.of(
                "token",      token,
                "expires_in", jwtProperties.getTtl(),
                "role",       domainUser.getRole().name()
        ));
    }
}
