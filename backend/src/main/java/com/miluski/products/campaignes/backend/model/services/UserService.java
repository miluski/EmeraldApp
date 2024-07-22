package com.miluski.products.campaignes.backend.model.services;

import java.util.Base64;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.miluski.products.campaignes.backend.model.dto.UserDto;
import com.miluski.products.campaignes.backend.model.entities.User;
import com.miluski.products.campaignes.backend.model.mappers.UserMapper;
import com.miluski.products.campaignes.backend.model.repositories.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Service class for user-related operations.
 * This class handles user authentication, token management, and user retrieval
 * operations.
 * It utilizes Spring's AuthenticationManager for authentication, a custom
 * JwtTokenService for JWT operations,
 * and interacts with the UserRepository for database operations.
 */
@Service
public class UserService {

    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;

    /**
     * Constructs a UserService with required dependencies.
     * 
     * @param userMapper            An instance of UserMapper for DTO and entity
     *                              conversions.
     * @param authenticationManager An instance of AuthenticationManager for user
     *                              authentication.
     * @param jwtTokenService       An instance of JwtTokenService for handling JWT
     *                              tokens.
     * @param userRepository        An instance of UserRepository for database
     *                              operations.
     */
    @Autowired
    public UserService(UserMapper userMapper, AuthenticationManager authenticationManager,
            JwtTokenService jwtTokenService, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.userRepository = userRepository;
    }

    /**
     * Authenticates a user and returns their details as a UserDto.
     * 
     * @param userDto The user data transfer object containing login credentials.
     * @return A UserDto with user details if authentication is successful, null
     *         otherwise.
     */
    public UserDto getAuthenticatedUserObject(UserDto userDto) {
        try {
            User user = userMapper.convertToUser(userDto);
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(),
                    user.getPassword());
            this.authenticationManager.authenticate(authentication);
            User retrievedUser = userRepository.findByUsername(userDto.getUsername());
            return userMapper.convertToUserDto(retrievedUser);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Validates if a user logout operation is correct by nullifying their refresh
     * token.
     * 
     * @param request The HttpServletRequest containing the Authorization header
     *                with the access token.
     * @return True if the logout operation is successful, false otherwise.
     */
    public Boolean getIsUserLogoutCorrectly(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization").substring(7);
        try {
            if (accessToken == null) {
                throw new Exception("No access token found");
            }
            String username = jwtTokenService.getUsername(accessToken);
            User user = userRepository.findByUsername(username);
            if (user != null) {
                user.setRefreshToken(null);
                userRepository.save(user);
            } else {
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Generates a new access token using a valid refresh token provided in the
     * request.
     * 
     * @param request The HttpServletRequest containing the Authorization header
     *                with the access token.
     * @return A new access token if the refresh token is valid.
     * @throws Exception if the refresh token is invalid or the user is not found.
     */
    public String getRefreshedAccessToken(HttpServletRequest request)
            throws Exception {
        String accessToken = request.getHeader("Authorization").substring(7);
        String username = extractUsernameFromJWT(accessToken);
        User user = userRepository.findByUsername(username);
        if (user != null) {
            Boolean isRefreshTokenValid = jwtTokenService.validateToken(user.getRefreshToken(), user.getUsername());
            if (isRefreshTokenValid) {
                return getAccessToken(user.getUsername());
            } else {
                throw new Exception("Refresh token is not valid");
            }
        } else {
            throw new Exception("User not found");
        }
    }

    /**
     * Generates a new access token and assigns a new refresh token to the user.
     * 
     * @param username The username of the user for whom to generate and assign
     *                 tokens.
     * @return The generated access token.
     */
    public String getAccessToken(String username) {
        String accessToken = jwtTokenService.generateToken(username);
        String refreshToken = jwtTokenService.generateRefreshToken(username);
        assignRefreshTokenToUser(username, refreshToken);
        return accessToken;
    }

    /**
     * Assigns a refresh token to a user and saves it in the database.
     * 
     * @param username     The username of the user to whom the refresh token is
     *                     assigned.
     * @param refreshToken The refresh token to be assigned.
     */
    public void assignRefreshTokenToUser(String username, String refreshToken) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setRefreshToken(refreshToken);
            userRepository.save(user);
        }
    }

    /**
     * Extracts the username from a JWT token.
     * 
     * @param jwt The JWT token from which to extract the username.
     * @return The username if extraction is successful, "Invalid JWT" otherwise.
     */
    private static String extractUsernameFromJWT(String jwt) {
        try {
            String[] parts = jwt.split("\\.");
            if (parts.length == 3) {
                String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
                JSONObject jsonObject = new JSONObject(payload);
                return jsonObject.optString("sub", "Username not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Invalid JWT";
    }

}
