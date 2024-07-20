package com.miluski.products.campaignes.backend;

import com.miluski.products.campaignes.backend.controller.AuthController;
import com.miluski.products.campaignes.backend.model.dto.UserDto;
import com.miluski.products.campaignes.backend.model.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authController = new AuthController(userService);
    }

    @Test
    void handleLoginRequest_ValidUser_ReturnsAuthenticatedUserObject() {
        UserDto userDto = new UserDto();
        when(userService.getAuthenticatedUserObject(userDto)).thenReturn(userDto);
        ResponseEntity<?> responseEntity = authController.handleLoginRequest(userDto, request, response);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userDto, responseEntity.getBody());
        verify(userService, times(1)).setSessionTokens(request, response, userDto.getUsername());
    }

    @Test
    void handleLoginRequest_InvalidUser_ReturnsForbiddenStatus() {
        UserDto userDto = new UserDto();
        when(userService.getAuthenticatedUserObject(userDto)).thenReturn(null);
        ResponseEntity<?> responseEntity = authController.handleLoginRequest(userDto, request, response);
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        verify(userService, never()).setSessionTokens(request, response, userDto.getUsername());
    }

    @Test
    void handleRefreshTokensRequest_Success_ReturnsOkStatus() throws Exception {
        doNothing().when(userService).handleRefreshToken(request, response);
        ResponseEntity<?> responseEntity = authController.handleRefreshTokensRequest(request, response);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void handleRefreshTokensRequest_Exception_ReturnsUnauthorizedStatus() throws Exception {
        doThrow(new RuntimeException("Invalid token")).when(userService).handleRefreshToken(request, response);
        ResponseEntity<?> responseEntity = authController.handleRefreshTokensRequest(request, response);
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

    @Test
    void handleLogoutRequest_UserLogoutCorrectly_ReturnsOkStatus() {
        when(userService.getIsUserLogoutCorrectly(request, response)).thenReturn(true);
        ResponseEntity<?> responseEntity = authController.handleLogoutRequest(request, response);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void handleLogoutRequest_UserLogoutIncorrectly_ReturnsForbiddenStatus() {
        when(userService.getIsUserLogoutCorrectly(request, response)).thenReturn(false);
        ResponseEntity<?> responseEntity = authController.handleLogoutRequest(request, response);
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }
}