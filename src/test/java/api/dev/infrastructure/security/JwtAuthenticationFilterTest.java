package api.dev.infrastructure.security;

import api.dev.infrastructure.security.filter.JwtAuthenticationFilter;
import api.dev.infrastructure.security.jwt.JwtService;
import api.dev.infrastructure.security.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@Transactional
@ExtendWith(MockitoExtension.class)
@DisplayName("JwtAuthenticationFilter")
class JwtAuthenticationFilterTest {

    @Mock private JwtService jwtService;
    @Mock private UserDetailsServiceImpl userDetailsService;
    @Mock private FilterChain chain;

    @InjectMocks
    private JwtAuthenticationFilter filter;

    // Use Spring's MockHttpServletRequest/Response instead of Mockito mocks —
    // they are real servlet objects so doFilter() / OncePerRequestFilter internals work correctly
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
        request  = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    @DisplayName("passes through when no Authorization header is present")
    void noHeader_continuesChain() throws Exception {
        // no Authorization header set

        filter.doFilter(request, response, chain);  // ← public method, always visible

        verify(chain).doFilter(request, response);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    @DisplayName("passes through when Authorization header is not Bearer")
    void nonBearerHeader_continuesChain() throws Exception {
        request.addHeader("Authorization", "Basic dXNlcjpwYXNz");

        filter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    @DisplayName("passes through unauthenticated when token is invalid")
    void invalidToken_continuesChainUnauthenticated() throws Exception {
        request.addHeader("Authorization", "Bearer invalid.token");
        when(jwtService.isTokenValid("invalid.token")).thenReturn(false);

        filter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
        verify(userDetailsService, never()).loadUserByUsername(any());
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    @DisplayName("sets authentication in SecurityContext when token is valid")
    void validToken_setsAuthentication() throws Exception {
        String token = "valid.jwt.token";
        request.addHeader("Authorization", "Bearer " + token);

        var userDetails = new User("master@test.com", "hashed",
                List.of(new SimpleGrantedAuthority("ROLE_MASTER")));

        when(jwtService.isTokenValid(token)).thenReturn(true);
        when(jwtService.extractEmail(token)).thenReturn("master@test.com");
        when(userDetailsService.loadUserByUsername("master@test.com")).thenReturn(userDetails);

        filter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
        assertThat(SecurityContextHolder.getContext().getAuthentication().getName())
                .isEqualTo("master@test.com");
    }
}
