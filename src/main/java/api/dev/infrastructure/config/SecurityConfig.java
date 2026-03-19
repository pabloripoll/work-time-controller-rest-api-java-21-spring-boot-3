package api.dev.infrastructure.config;

import api.dev.infrastructure.security.filter.JwtAuthenticationFilter;
import api.dev.infrastructure.security.handler.*;
import api.dev.infrastructure.security.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity   // enables @PreAuthorize on controllers
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthenticationFilter jwtFilter;
    private final AuthSuccessHandler successHandler;
    private final AuthFailureHandler failureHandler;
    private final JwtAuthenticationEntryPoint entryPoint;
    private final ApiAccessDeniedHandler accessDeniedHandler;
    private final BCryptPasswordEncoder passwordEncoder;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService,
                           JwtAuthenticationFilter jwtFilter,
                           AuthSuccessHandler successHandler,
                           AuthFailureHandler failureHandler,
                           JwtAuthenticationEntryPoint entryPoint,
                           ApiAccessDeniedHandler accessDeniedHandler,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userDetailsService  = userDetailsService;
        this.jwtFilter           = jwtFilter;
        this.successHandler      = successHandler;
        this.failureHandler      = failureHandler;
        this.entryPoint          = entryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
        this.passwordEncoder     = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // --- Login endpoints (equivalent to Symfony json_login firewalls) ---
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())

            // Master login
            .securityMatcher("/api/**")
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/master/auth/login").permitAll()
                .requestMatchers("/api/v1/admin/auth/login").permitAll()
                .requestMatchers("/api/v1/auth/login").permitAll()
                .requestMatchers("/api/v1/auth/refresh").permitAll()   // controller validates token itself
                .requestMatchers("/api/v1/master/**").hasRole("MASTER")
                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/account/**").hasRole("EMPLOYEE")
                .anyRequest().authenticated()
            )

            // --- Error handlers (equivalent to entry_point and access_denied_handler) ---
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(entryPoint)
                .accessDeniedHandler(accessDeniedHandler)
            )

            // --- JWT filter runs before Spring's auth filter ---
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

            // --- Login processing (equivalent to json_login) ---
            .addFilter(masterLoginFilter())
            .addFilter(adminLoginFilter())
            .addFilter(employeeLoginFilter());

        return http.build();
    }

    private UsernamePasswordAuthenticationFilter masterLoginFilter() throws Exception {
        var filter = new UsernamePasswordAuthenticationFilter(authenticationManager(null));
        filter.setFilterProcessesUrl("/api/v1/master/auth/login");
        filter.setUsernameParameter("email");
        filter.setPasswordParameter("password");
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationFailureHandler(failureHandler);

        return filter;
    }

    private UsernamePasswordAuthenticationFilter adminLoginFilter() throws Exception {
        var filter = new UsernamePasswordAuthenticationFilter(authenticationManager(null));
        filter.setFilterProcessesUrl("/api/v1/admin/auth/login");
        filter.setUsernameParameter("email");
        filter.setPasswordParameter("password");
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationFailureHandler(failureHandler);

        return filter;
    }

    private UsernamePasswordAuthenticationFilter employeeLoginFilter() throws Exception {
        var filter = new UsernamePasswordAuthenticationFilter(authenticationManager(null));
        filter.setFilterProcessesUrl("/api/v1/auth/login");
        filter.setUsernameParameter("email");
        filter.setPasswordParameter("password");
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationFailureHandler(failureHandler);

        return filter;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        var provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
