package com.miluski.products.campaignes.backend.model;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.miluski.products.campaignes.backend.model.services.JwtTokenService;
import com.miluski.products.campaignes.backend.model.services.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Custom filter for JWT authentication.
 * This filter extends Spring Security's OncePerRequestFilter to ensure it is
 * executed once per request.
 * It intercepts incoming requests to validate JWT tokens and sets the
 * authentication in the security context if the token is valid.
 * 
 * The filter bypasses authentication for specific paths related to login,
 * logout, and token refresh operations.
 */
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    /**
     * Constructs a JwtRequestFilter with dependencies.
     * 
     * @param jwtTokenService        Service for handling JWT token operations.
     * @param userDetailsServiceImpl Service for loading user details by username.
     */
    @Autowired
    public JwtRequestFilter(JwtTokenService jwtTokenService, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.jwtTokenService = jwtTokenService;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    /**
     * Filters incoming requests for JWT authentication.
     * It checks the request URI to bypass authentication for login, logout, and
     * token refresh endpoints.
     * For other requests, it extracts the JWT token from the Authorization header,
     * validates it,
     * and sets the authentication in the security context if the token is valid.
     * 
     * @param request     The HttpServletRequest being processed.
     * @param response    The HttpServletResponse associated with the request.
     * @param filterChain The filter chain for invoking the next filter or the
     *                    resource at the end of the chain.
     * @throws ServletException If an exception occurs that interferes with the
     *                          filter's normal operation.
     * @throws IOException      If an I/O error occurs during the processing of the
     *                          request.
     */
    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (requestURI.equals("/api/auth/user/login") || requestURI.equals("/api/auth/user/logout")
                || requestURI.equals("/api/auth/tokens/refresh")) {
            filterChain.doFilter(request, response);
            return;
        }
        String requestHeader = request.getHeader("Authorization");
        String token = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            token = requestHeader.substring(7);
        }
        if (token != null) {
            String username = jwtTokenService.getUsername(token);
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
            if (jwtTokenService.validateToken(token, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

}
