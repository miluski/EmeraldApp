package com.miluski.products.campaignes.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.miluski.products.campaignes.backend.model.dto.UserDto;
import com.miluski.products.campaignes.backend.model.entities.User;
import com.miluski.products.campaignes.backend.model.mappers.UserMapper;
import com.miluski.products.campaignes.backend.model.repositories.UserRepository;
import com.miluski.products.campaignes.backend.model.services.JwtTokenService;
import com.miluski.products.campaignes.backend.model.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenService jwtTokenService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAuthenticatedUserObject_ValidUserDto_ReturnsUserDto() {
        UserDto userDto = new UserDto(1L, "testUser", 100d);
        User user = new User("testUser", "testPassword");
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        when(userMapper.convertToUser(userDto)).thenReturn(user);
        when(authenticationManager.authenticate(authentication)).thenReturn(null);
        when(userRepository.findByUsername(userDto.getUsername())).thenReturn(user);
        when(userMapper.convertToUserDto(user)).thenReturn(userDto);
        UserDto result = userService.getAuthenticatedUserObject(userDto);
        assertNotNull(result);
        assertEquals(userDto, result);
        verify(userMapper, times(1)).convertToUser(userDto);
        verify(authenticationManager, times(1)).authenticate(authentication);
        verify(userRepository, times(1)).findByUsername(userDto.getUsername());
        verify(userMapper, times(1)).convertToUserDto(user);
    }

    @Test
    void getAuthenticatedUserObject_InvalidUserDto_ReturnsNull() {
        UserDto userDto = new UserDto(1L, "testUser", 100d);
        User user = new User("testUser", "testPassword");
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        when(userMapper.convertToUser(userDto)).thenReturn(user);
        UserDto result = userService.getAuthenticatedUserObject(userDto);
        assertNull(result);
        verify(userMapper, times(1)).convertToUser(userDto);
        verify(authenticationManager, times(1)).authenticate(authentication);
    }

    @Test
    void getRefreshedAccessToken_InvalidAccessToken_ReturnsException() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer testAccessToken");
        String username = "testUser";
        User user = new User("testUser", "testPassword");
        when(jwtTokenService.getUsername(any())).thenReturn(username);
        when(userRepository.findByUsername(username)).thenReturn(user);
        when(jwtTokenService.validateToken(null, username)).thenReturn(false);
        assertThrows(Exception.class, () -> userService.getRefreshedAccessToken(request));
        verify(userRepository, times(1)).findByUsername("Invalid JWT");
    }

    @Test
    void getRefreshedAccessToken_UserNotFound_ReturnsException() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer testAccessToken");
        String username = "testUser";
        when(jwtTokenService.getUsername(any())).thenReturn(username);
        when(userRepository.findByUsername(username)).thenReturn(null);
        assertThrows(Exception.class, () -> userService.getRefreshedAccessToken(request));
        verify(userRepository, times(1)).findByUsername("Invalid JWT");
    }

    @Test
    void getAccessToken_ReturnsAccessToken() {
        String username = "testUser";
        String accessToken = "testAccessToken";
        String refreshToken = "testRefreshToken";
        User user = new User("testUser", "testPassword");
        when(jwtTokenService.generateToken(username)).thenReturn(accessToken);
        when(jwtTokenService.generateRefreshToken(username)).thenReturn(refreshToken);
        when(userRepository.findByUsername(username)).thenReturn(user);
        String result = userService.getAccessToken(username);
        assertEquals(accessToken, result);
        assertEquals(refreshToken, user.getRefreshToken());
        verify(jwtTokenService, times(1)).generateToken(username);
        verify(jwtTokenService, times(1)).generateRefreshToken(username);
        verify(userRepository, times(1)).findByUsername(username);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void assignRefreshTokenToUser_UserNotFound_DoesNotSaveUser() {
        String username = "testUser";
        String refreshToken = "testRefreshToken";
        when(userRepository.findByUsername(username)).thenReturn(null);
        userService.assignRefreshTokenToUser(username, refreshToken);
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void assignRefreshTokenToUser_UserFound_SavesUserWithRefreshToken() {
        String username = "testUser";
        String refreshToken = "testRefreshToken";
        User user = new User("testUser", "testPassword");
        when(userRepository.findByUsername(username)).thenReturn(user);
        userService.assignRefreshTokenToUser(username, refreshToken);
        assertEquals(refreshToken, user.getRefreshToken());
        verify(userRepository, times(1)).findByUsername(username);
        verify(userRepository, times(1)).save(user);
    }
}