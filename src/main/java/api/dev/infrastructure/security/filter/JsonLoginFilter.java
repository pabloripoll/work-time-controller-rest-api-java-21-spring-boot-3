package api.dev.infrastructure.security.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

public class JsonLoginFilter extends OncePerRequestFilter {

    private final AntPathRequestMatcher matcher;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;
    private final ObjectMapper objectMapper;

    public JsonLoginFilter(String loginUrl,
                           AuthenticationManager authenticationManager,
                           AuthenticationSuccessHandler successHandler,
                           AuthenticationFailureHandler failureHandler,
                           ObjectMapper objectMapper) {
        this.matcher               = new AntPathRequestMatcher(loginUrl, "POST");
        this.authenticationManager = authenticationManager;
        this.successHandler        = successHandler;
        this.failureHandler        = failureHandler;
        this.objectMapper          = objectMapper;
    }

    @SuppressWarnings("null")
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        boolean skip = !matcher.matches(request);

        return skip;
    }

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain
    ) throws ServletException, IOException {
        try {
            Map<String, Object> body = objectMapper.readValue(
                request.getInputStream(),
                new TypeReference<Map<String, Object>>() {}
            );

            String email    = (String) body.getOrDefault("email", "");
            String password = (String) body.getOrDefault("password", "");

            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email.trim(), password)
            );

            successHandler.onAuthenticationSuccess(request, response, auth);

        } catch (AuthenticationException ex) {
            failureHandler.onAuthenticationFailure(request, response, ex);
        }
        // Note: do NOT call chain.doFilter() — the response is fully written by the handlers
    }
}
