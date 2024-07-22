package com.miluski.products.campaignes.backend.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfiguration;

import com.miluski.products.campaignes.backend.model.services.JwtTokenService;
import com.miluski.products.campaignes.backend.model.services.UserDetailsServiceImpl;

import jakarta.servlet.http.HttpServletResponse;

/**
 * Security configuration class for the application.
 * This class configures Spring Security for the application, including CORS,
 * CSRF, session management,
 * authentication, and authorization rules. It also defines beans for the
 * authentication manager,
 * security filter chain, and session event publisher.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtTokenService jwtTokenService;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    /**
     * Constructs the SecurityConfig with required services.
     * 
     * @param userDetailsServiceImpl Service for loading user details.
     * @param jwtTokenService        Service for JWT token operations.
     */
    @Autowired
    public SecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl, JwtTokenService jwtTokenService) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.jwtTokenService = jwtTokenService;
    }

    /**
     * Configures the security filter chain for HTTP requests.
     * This method sets up the security rules for CSRF, CORS, headers, session
     * management, authorization,
     * exception handling, and adds a custom JWT request filter before the
     * UsernamePasswordAuthenticationFilter.
     * 
     * @param httpSecurity The HttpSecurity to configure.
     * @return The configured SecurityFilterChain.
     * @throws Exception if an error occurs during the configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf((csrf) -> csrf.disable())
                .cors((cors) -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowCredentials(true);
                    config.addAllowedOrigin("https://emerald-app-88c863e81f66.herokuapp.com");
                    config.addAllowedHeader("*");
                    config.addAllowedMethod("*");
                    return config;
                }))
                .headers((headers) -> headers
                        .frameOptions((frameOptions) -> frameOptions.disable()))
                .sessionManagement((sessionManagement) -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.NEVER)
                        .maximumSessions(1))
                .authorizeHttpRequests(
                        (auth) -> auth
                                .requestMatchers("/api/auth/user/login").permitAll()
                                .requestMatchers("/api/auth/user/logout").permitAll()
                                .requestMatchers("/api/auth/tokens/refresh").permitAll()
                                .requestMatchers("/h2-console/**").permitAll()
                                .anyRequest().authenticated())
                .exceptionHandling((exceptionHandling) -> exceptionHandling.authenticationEntryPoint(
                        ((request, response, exception) -> {
                            System.out.println(exception.getMessage());
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                        })))
                .addFilterBefore(new JwtRequestFilter(jwtTokenService, userDetailsServiceImpl),
                        UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    /**
     * Configures the AuthenticationManager bean.
     * This method sets up the DaoAuthenticationProvider with a custom user details
     * service and password encoder.
     * 
     * @return The configured AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        Argon2PasswordEncoder argon2PasswordEncoder = new Argon2PasswordEncoder(16, 32, 1, 60000, 10);
        daoAuthenticationProvider.setUserDetailsService(userDetailsServiceImpl);
        daoAuthenticationProvider.setPasswordEncoder(argon2PasswordEncoder);
        return new ProviderManager(daoAuthenticationProvider);
    }

    /**
     * Configures the HttpSessionEventPublisher bean.
     * This bean is responsible for publishing session creation and destruction
     * events to Spring Security.
     * 
     * @return The HttpSessionEventPublisher bean.
     */
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}
