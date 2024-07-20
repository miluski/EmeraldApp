package com.miluski.products.campaignes.backend;
import com.miluski.products.campaignes.backend.model.dto.UserDto;
import com.miluski.products.campaignes.backend.model.entities.User;
import com.miluski.products.campaignes.backend.model.mappers.UserMapper;
import com.miluski.products.campaignes.backend.model.repositories.UserRepository;
import com.miluski.products.campaignes.backend.model.services.JwtTokenService;
import com.miluski.products.campaignes.backend.model.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenService jwtTokenService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userMapper, authenticationManager, jwtTokenService, userRepository);
    }

    @Test
    void getAuthenticatedUserObject_InvalidUser_ReturnsNull() {
        UserDto userDto = new UserDto();
        when(userMapper.convertToUser(userDto)).thenReturn(new User());
        when(authenticationManager.authenticate(any(Authentication.class))).thenThrow(new UsernameNotFoundException("User not found"));
        UserDto result = userService.getAuthenticatedUserObject(userDto);
        assertNull(result);
        verify(userMapper, times(1)).convertToUser(userDto);
        verify(authenticationManager, times(1)).authenticate(any(Authentication.class));
        verify(userRepository, never()).findByUsername(anyString());
    }

    @Test
    void getIsUserLogoutCorrectly_InvalidAccessToken_ReturnsFalse() {
        String accessToken = null;
        when(jwtTokenService.getTokenFromCookies(request)).thenReturn(accessToken);
        Boolean result = userService.getIsUserLogoutCorrectly(request, response);
        assertFalse(result);
        verify(jwtTokenService, times(1)).getTokenFromCookies(request);
        verify(jwtTokenService, never()).getUsername(anyString());
        verify(userRepository, never()).findByUsername(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void handleRefreshToken_ValidUser_ReturnsOkStatus() throws Exception {
        String accessToken = "valid_access_token";
        String username = "test_user";
        User user = new User();
        user.setRefreshToken("valid_refresh_token");
        when(jwtTokenService.getTokenFromCookies(request)).thenReturn(accessToken);
        when(jwtTokenService.getUsername(accessToken)).thenReturn(username);
        when(userRepository.findByUsername(username)).thenReturn(user);
        when(jwtTokenService.validateToken(user.getRefreshToken(), user.getUsername())).thenReturn(true);        userService.handleRefreshToken(request, response);
        verify(jwtTokenService, times(1)).getTokenFromCookies(request);
        verify(jwtTokenService, times(1)).getUsername(accessToken);
        verify(userRepository, times(1)).findByUsername(username);
        verify(jwtTokenService, times(1)).validateToken(user.getRefreshToken(), user.getUsername());    }

    @Test
    void handleRefreshToken_InvalidUser_ThrowsException() throws Exception {
        String accessToken = "valid_access_token";
        String username = "test_user";
        when(jwtTokenService.getTokenFromCookies(request)).thenReturn(accessToken);
        when(jwtTokenService.getUsername(accessToken)).thenReturn(username);
        when(userRepository.findByUsername(username)).thenReturn(null);
        assertThrows(Exception.class, () -> userService.handleRefreshToken(request, response));
        verify(jwtTokenService, times(1)).getTokenFromCookies(request);
        verify(jwtTokenService, times(1)).getUsername(accessToken);
        verify(userRepository, times(1)).findByUsername(username);
        verify(jwtTokenService, never()).validateToken(anyString(), anyString());    }

    @Test
    void setSessionTokens_ValidUsername_SetsSessionTokens() {
        String username = "test_user";
        String accessToken = "valid_access_token";
        String refreshToken = "valid_refresh_token";
        User user = new User();
        when(jwtTokenService.generateToken(username)).thenReturn(accessToken);
        when(jwtTokenService.generateRefreshToken(username)).thenReturn(refreshToken);
        when(userRepository.findByUsername(username)).thenReturn(user);
        userService.setSessionTokens(request, response, username);
        verify(request, times(1)).getSession(true);
        verify(jwtTokenService, times(1)).generateToken(username);
        verify(jwtTokenService, times(1)).generateRefreshToken(username);
        verify(userRepository, times(1)).findByUsername(username);
        verify(userRepository, times(1)).save(user);
        verify(response, times(1)).addCookie(any());
    }

    @Test
    void setSessionTokens_InvalidUsername_DoesNotSetSessionTokens() {
        String username = "test_user";
        when(userRepository.findByUsername(username)).thenReturn(null);
        userService.setSessionTokens(request, response, username);
        verify(request, times(1)).getSession(true);
        verify(userRepository, times(1)).findByUsername(username);
        verify(userRepository, never()).save(any(User.class));    }
}