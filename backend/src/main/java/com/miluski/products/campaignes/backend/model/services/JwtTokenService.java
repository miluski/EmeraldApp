package com.miluski.products.campaignes.backend.model.services;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Service class for JWT (JSON Web Token) operations.
 * This class provides methods for generating, parsing, and validating JWTs for
 * user authentication.
 * It supports the creation of both access tokens and refresh tokens with
 * different expiration times.
 * Additionally, it includes utility methods for extracting tokens from HTTP
 * requests and validating their expiration and correctness.
 */
@Service
public class JwtTokenService {

    @Value("${jwt.secret}")
    public String secret;

    /**
     * Generates a JWT access token for a given username.
     * The token includes the username as a subject, the issue time, and an
     * expiration time set to 10 minutes from the issue time.
     * 
     * @param username The username for which the access token is generated.
     * @return A JWT access token as a String.
     */
    public String generateToken(String username) {
        return Jwts.builder().setClaims(new HashMap<>()).setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    /**
     * Generates a JWT refresh token for a given username.
     * Similar to the access token, but with a longer expiration time set to 7 days
     * from the issue time.
     * 
     * @param username The username for which the refresh token is generated.
     * @return A JWT refresh token as a String.
     */
    public String generateRefreshToken(String username) {
        return Jwts.builder().setClaims(new HashMap<>()).setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 604800000))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    /**
     * Validates a JWT token by comparing the username in the token with the
     * provided username and checking the token's expiration.
     * 
     * @param token    The JWT token to validate.
     * @param username The username to compare against the one in the token.
     * @return True if the token is valid, false otherwise.
     */
    public Boolean validateToken(String token, String username) {
        final String tokenUsername = this.getUsername(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }

    /**
     * Checks if a JWT token is expired.
     * 
     * @param token The JWT token to check.
     * @return True if the token is expired, false otherwise.
     */
    public Boolean isTokenExpired(String token) {
        final Date expirationDate = this.getExpirationDate(token);
        return expirationDate.before(new Date());
    }

    /*
     * Retrieves the expiration date of a JWT token.
     * 
     * @param token The JWT token.
     * 
     * @return The expiration date of the token.
     */
    public Date getExpirationDate(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    /**
     * Retrieves the username from a JWT token.
     * 
     * @param token The JWT token.
     * @return The username contained in the token.
     */
    public String getUsername(String token) {
        return getClaim(token, Claims::getSubject);
    }

    /**
     * Retrieves a specific claim from a JWT token.
     * This method uses a Function to extract a specific claim from the JWT token's
     * claims.
     * It is a generic method that can be used to retrieve any type of claim, such
     * as the subject, issue date, etc.
     * 
     * @param token          The JWT token from which the claim is to be retrieved.
     * @param claimsResolver A Function that takes the Claims object and returns a
     *                       specific claim of type T.
     * @return The claim of type T.
     */
    private <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Retrieves all claims from a JWT token.
     * This method decodes the JWT token using the signing key and returns its
     * claims.
     * 
     * @param token The JWT token from which the claims are to be retrieved.
     * @return A Claims object containing all the claims of the JWT token.
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    /**
     * Generates the signing key from the secret.
     * This method creates a new signing key using the specified secret and the
     * HmacSHA256 algorithm.
     * 
     * @return A Key object to be used for signing or verifying JWT tokens.
     */
    private Key getSignKey() {
        return new SecretKeySpec(this.secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
    }
}
