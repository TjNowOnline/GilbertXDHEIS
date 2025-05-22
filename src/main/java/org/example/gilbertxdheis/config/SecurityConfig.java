package org.example.gilbertxdheis.config;

import org.example.gilbertxdheis.application.UserDetailsServiceImpl;
import org.example.gilbertxdheis.infrastructure.JdbcUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JdbcUserRepository userRepository;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, JdbcUserRepository userRepository) {
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/login", "/register", "/sell", "/css/**", "/js/**", "/uploads/**").permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/moderator/**").hasRole("MODERATOR")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler(customAuthenticationSuccessHandler())
                        .failureHandler(customAuthenticationFailureHandler())
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .csrf(csrf -> csrf
                        .csrfTokenRepository(csrfTokenRepository())
                        // Temporarily disable CSRF for debugging
                        .ignoringRequestMatchers("/api/**")
                );
        return http.build();
    }

    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        return new HttpSessionCsrfTokenRepository();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            String email = authentication.getName();
            userRepository.updateLastLogin(email);

            // Retrieve userId and store it in the session
            Long userId = Long.valueOf(userRepository.findByEmail(email)
                    .map(user -> user.getUserId())
                    .orElse(null));

            if (userId != null) {
                request.getSession().setAttribute("userId", userId);
                System.out.println("User logged in with userId: " + userId);
            } else {
                System.out.println("Failed to retrieve userId for email: " + email);
            }

            // Redirect based on roles
            if (authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority ->
                            grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
                response.sendRedirect("/admin");
            } else if (authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority ->
                            grantedAuthority.getAuthority().equals("ROLE_MODERATOR"))) {
                response.sendRedirect("/moderator");
            } else {
                response.sendRedirect("/my-profile");
            }
        };
    }

    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return (request, response, exception) -> {
            System.out.println("Login failed: " + exception.getMessage());
            response.sendRedirect("/login?error=true");
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}