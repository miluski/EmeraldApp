package com.miluski.products.campaignes.backend;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.miluski.products.campaignes.backend.model.services.JwtTokenService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;

class JwtTokenServiceTest {

    @InjectMocks
    private JwtTokenService jwtTokenService;

    @Mock
    private HttpServletRequest httpServletRequest;

    private String secret = "181dcc2c53cadb9c8855eb6e296ce0ef8628ceee10b5c4b10f1913a00485f08b";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(jwtTokenService, "secret", this.secret);
    }

    @Test
    void generateToken_ValidUsername_ReturnsToken() {
        String username = "testUser";
        String token = jwtTokenService.generateToken(username);
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void generateRefreshToken_ValidUsername_ReturnsRefreshToken() {
        String username = "testUser";
        String refreshToken = jwtTokenService.generateRefreshToken(username);
        assertNotNull(refreshToken);
        assertTrue(refreshToken.length() > 0);
    }

    @Test
    void validateToken_ValidTokenAndUsername_ReturnsTrue() {
        String username = "testUser";
        String token = generateTestToken(username, new Date(System.currentTimeMillis() + 600000));
        boolean isValid = jwtTokenService.validateToken(token, username);
        assertTrue(isValid);
    }

    @Test
    void validateToken_ExpiredToken_ReturnsFalse() {
        String username = "testUser";
        String token = generateTestToken(username, new Date(System.currentTimeMillis() - 600000));
        assertThrows(Exception.class, () -> jwtTokenService.validateToken(token, username));
    }

    @Test
    void validateToken_InvalidUsername_ReturnsFalse() {
        String username = "testUser";
        String token = generateTestToken(username, new Date(System.currentTimeMillis() + 600000));
        boolean isValid = jwtTokenService.validateToken(token, "invalidUser");
        assertFalse(isValid);
    }

    @Test
    void isTokenExpired_NotExpiredToken_ReturnsFalse() {
        String username = "testUser";
        String token = generateTestToken(username, new Date(System.currentTimeMillis() + 600000));
        boolean isExpired = jwtTokenService.isTokenExpired(token);
        assertFalse(isExpired);
    }

    @Test
    void isTokenExpired_ExpiredToken_ReturnsTrue() {
        String username = "testUser";
        String token = generateTestToken(username, new Date(System.currentTimeMillis() - 600000));
        assertThrows(Exception.class, () -> jwtTokenService.isTokenExpired(token));
    }

    @Test
    void getExpirationDate_ValidToken_ReturnsExpirationDate() {
        String username = "testUser";
        Date expirationDate = new Date(System.currentTimeMillis() + 600000);
        String token = generateTestToken(username, expirationDate);
        Date actualExpirationDate = jwtTokenService.getExpirationDate(token);
        assertNotNull(actualExpirationDate);
        long difference = Math.abs(expirationDate.getTime() - actualExpirationDate.getTime());
        assertTrue(difference < 1000, "The dates differ by more than 1 second");
    }

    @Test
    void getUsername_ValidToken_ReturnsUsername() {
        String username = "testUser";
        String token = generateTestToken(username, new Date(System.currentTimeMillis() + 600000));
        String actualUsername = jwtTokenService.getUsername(token);
        assertNotNull(actualUsername);
        assertEquals(username, actualUsername);
    }

    private String generateTestToken(String username, Date expirationDate) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        return new SecretKeySpec(this.secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
    }
}