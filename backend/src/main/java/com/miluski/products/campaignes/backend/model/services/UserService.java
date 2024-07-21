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

@Service
public class UserService {

    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserMapper userMapper, AuthenticationManager authenticationManager,
            JwtTokenService jwtTokenService, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.userRepository = userRepository;
    }

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

    public String getAccessToken(String username) {
        String accessToken = jwtTokenService.generateToken(username);
        String refreshToken = jwtTokenService.generateRefreshToken(username);
        assignRefreshTokenToUser(username, refreshToken);
        return accessToken;
    }

    public void assignRefreshTokenToUser(String username, String refreshToken) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setRefreshToken(refreshToken);
            userRepository.save(user);
        }
    }

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
