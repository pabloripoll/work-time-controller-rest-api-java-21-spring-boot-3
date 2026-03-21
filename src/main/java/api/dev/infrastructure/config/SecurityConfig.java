package api.dev.infrastructure.config;

import api.dev.infrastructure.security.filter.JwtAuthenticationFilter;
import api.dev.infrastructure.security.filter.JsonLoginFilter;
import api.dev.infrastructure.security.handler.*;
import api.dev.infrastructure.security.service.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Profile("!seed")
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthenticationFilter jwtFilter;
    private final AuthSuccessHandler successHandler;
    private final AuthFailureHandler failureHandler;
    private final JwtAuthenticationEntryPoint entryPoint;
    private final ApiAccessDeniedHandler accessDeniedHandler;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService,
                           JwtAuthenticationFilter jwtFilter,
                           AuthSuccessHandler successHandler,
                           AuthFailureHandler failureHandler,
                           JwtAuthenticationEntryPoint entryPoint,
                           ApiAccessDeniedHandler accessDeniedHandler,
                           BCryptPasswordEncoder passwordEncoder,
                           ObjectMapper objectMapper) {
        this.userDetailsService  = userDetailsService;
        this.jwtFilter           = jwtFilter;
        this.successHandler      = successHandler;
        this.failureHandler      = failureHandler;
        this.entryPoint          = entryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
        this.passwordEncoder     = passwordEncoder;
        this.objectMapper        = objectMapper;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())
            .authorizeHttpRequests(auth -> auth
                // ── Public login endpoints
                .requestMatchers("/api/v1/master/auth/login").permitAll()
                .requestMatchers("/api/v1/admin/auth/login").permitAll()
                .requestMatchers("/api/v1/auth/login").permitAll()
                .requestMatchers("/api/v1/auth/refresh").permitAll()
                // ── Role-protected areas — must be authenticated
                .requestMatchers("/api/v1/master/**").hasRole("MASTER")
                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/account/**").hasRole("EMPLOYEE")
                // ── Everything else is public
                .anyRequest().permitAll()
            )
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(entryPoint)
                .accessDeniedHandler(accessDeniedHandler)
            )
            /* .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterAt(masterLoginFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterAfter(adminLoginFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterAfter(employeeLoginFilter(), UsernamePasswordAuthenticationFilter.class); */
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(masterLoginFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(adminLoginFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(employeeLoginFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private JsonLoginFilter masterLoginFilter() throws Exception {
        return new JsonLoginFilter(
            "/api/v1/master/auth/login",
            authenticationManager(),
            successHandler,
            failureHandler,
            objectMapper
        );
    }

    private JsonLoginFilter adminLoginFilter() throws Exception {
        return new JsonLoginFilter(
            "/api/v1/admin/auth/login",
            authenticationManager(),
            successHandler,
            failureHandler,
            objectMapper
        );
    }

    private JsonLoginFilter employeeLoginFilter() throws Exception {
        return new JsonLoginFilter(
            "/api/v1/auth/login",
            authenticationManager(),
            successHandler,
            failureHandler,
            objectMapper
        );
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        var provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationProvider()::authenticate;
    }
}
