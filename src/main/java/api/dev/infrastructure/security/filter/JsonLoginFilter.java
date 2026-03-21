package api.dev.infrastructure.security.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.Map;

public class JsonLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;

    public JsonLoginFilter(String loginUrl,
                           AuthenticationManager authenticationManager,
                           AuthenticationSuccessHandler successHandler,
                           AuthenticationFailureHandler failureHandler,
                           ObjectMapper objectMapper) {
        super(new AntPathRequestMatcher(loginUrl, "POST"));
        setAuthenticationManager(authenticationManager);
        setAuthenticationSuccessHandler(successHandler);
        setAuthenticationFailureHandler(failureHandler);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException, IOException {

        Map<String, Object> body = objectMapper.readValue(
            request.getInputStream(),
            new TypeReference<Map<String, Object>>() {}  // ← typed, no raw Map warning
        );

        String email    = (String) body.getOrDefault("email", "");
        String password = (String) body.getOrDefault("password", "");

        return getAuthenticationManager().authenticate(
            new UsernamePasswordAuthenticationToken(email.trim(), password)
        );
    }
}
