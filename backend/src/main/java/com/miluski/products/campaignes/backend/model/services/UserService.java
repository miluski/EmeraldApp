package com.miluski.products.campaignes.backend.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import com.miluski.products.campaignes.backend.model.dto.UserDto;
import com.miluski.products.campaignes.backend.model.entities.User;
import com.miluski.products.campaignes.backend.model.mappers.UserMapper;
import com.miluski.products.campaignes.backend.model.repositories.UserRepository;

import jakarta.servlet.http.*;

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

    public Boolean getIsUserLogoutCorrectly(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        try {
            String accessToken = jwtTokenService.getTokenFromCookies(httpServletRequest);
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
            removeHttpSession(httpServletRequest, httpServletResponse);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void handleRefreshToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws Exception {
        String accessToken = jwtTokenService.getTokenFromCookies(httpServletRequest);
        String username = jwtTokenService.getUsername(accessToken);
        User user = userRepository.findByUsername(username);
        if (user != null) {
            Boolean isRefreshTokenValid = jwtTokenService.validateToken(user.getRefreshToken(), user.getUsername());
            if (isRefreshTokenValid) {
                setSessionTokens(httpServletRequest, httpServletResponse, user.getUsername());
            } else {
                throw new Exception("Refresh token is not valid");
            }
        } else {
            throw new Exception("User not found");
        }
    }

    public void setSessionTokens(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            String username) {
        httpServletRequest.getSession(true);
        String accessToken = jwtTokenService.generateToken(username);
        String refreshToken = jwtTokenService.generateRefreshToken(username);
        assignRefreshTokenToUser(username, refreshToken);
        httpServletResponse.addCookie(getCookie("access_token", accessToken));
    }

    private void assignRefreshTokenToUser(String username, String refreshToken) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setRefreshToken(refreshToken);
            userRepository.save(user);
        }
    }

    private Cookie getCookie(String cookieName, String token) {
        Cookie cookie = new Cookie(cookieName, token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setDomain("emerald-app-88c863e81f66.herokuapp.com");
        return cookie;
    }

    private void removeHttpSession(HttpServletRequest httpServletRequest, HttpServletResponse response)
            throws IllegalStateException {
        HttpSession httpSession = httpServletRequest.getSession(false);
        Cookie[] cookies = httpServletRequest.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            cookie.setValue(null);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        httpSession.invalidate();
    }
}
