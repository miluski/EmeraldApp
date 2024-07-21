package com.miluski.products.campaignes.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.miluski.products.campaignes.backend.controller.AuthController;
import com.miluski.products.campaignes.backend.model.dto.UserDto;
import com.miluski.products.campaignes.backend.model.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleLoginRequest_ValidUserDto_ReturnsAuthenticatedUserObject() {
        UserDto userDto = new UserDto(1L, "testUser", 100d);
        UserDto authenticatedUserObject = new UserDto(1L, "testUser", 100d);
        when(userService.getAuthenticatedUserObject(userDto)).thenReturn(authenticatedUserObject);
        ResponseEntity<?> responseEntity = authController.handleLoginRequest(userDto);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("userCredentials", authenticatedUserObject);
        responseBody.put("accessToken", null);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseBody, responseEntity.getBody());
        verify(userService, times(1)).getAuthenticatedUserObject(userDto);
    }

    @Test
    void handleLoginRequest_InvalidUserDto_ReturnsForbiddenStatus() {
        UserDto userDto = new UserDto(1L, "testUser", 100d);
        when(userService.getAuthenticatedUserObject(userDto)).thenReturn(null);
        ResponseEntity<?> responseEntity = authController.handleLoginRequest(userDto);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(userService, times(1)).getAuthenticatedUserObject(userDto);
    }

    @Test
    void handleRefreshTokensRequest_ValidAccessToken_ReturnsRefreshedAccessToken() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer testAccessToken");
        String refreshedAccessToken = "refreshedAccessToken";
        when(userService.getRefreshedAccessToken(request)).thenReturn(refreshedAccessToken);
        ResponseEntity<?> responseEntity = authController.handleRefreshTokensRequest(request);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(refreshedAccessToken, responseEntity.getBody());
        verify(userService, times(1)).getRefreshedAccessToken(request);
    }

    @Test
    void handleRefreshTokensRequest_InvalidAccessToken_ReturnsUnauthorizedStatus() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer testAccessToken");
        when(userService.getRefreshedAccessToken(request)).thenThrow(new Exception("Invalid access token"));
        ResponseEntity<?> responseEntity = authController.handleRefreshTokensRequest(request);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(userService, times(1)).getRefreshedAccessToken(request);
    }

    @Test
    void handleLogoutRequest_ValidAccessToken_ReturnsOkStatus() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer testAccessToken");
        Boolean isUserLoggedOutCorrectly = true;
        when(userService.getIsUserLogoutCorrectly(request)).thenReturn(isUserLoggedOutCorrectly);
        ResponseEntity<?> responseEntity = authController.handleLogoutRequest(request);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(userService, times(1)).getIsUserLogoutCorrectly(request);
    }

    @Test
    void handleLogoutRequest_InvalidAccessToken_ReturnsForbiddenStatus() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer testAccessToken");
        Boolean isUserLoggedOutCorrectly = false;
        when(userService.getIsUserLogoutCorrectly(request)).thenReturn(isUserLoggedOutCorrectly);
        ResponseEntity<?> responseEntity = authController.handleLogoutRequest(request);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(userService, times(1)).getIsUserLogoutCorrectly(request);
    }
}