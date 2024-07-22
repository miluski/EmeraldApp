package com.miluski.products.campaignes.backend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.miluski.products.campaignes.backend.model.dto.UserDto;
import com.miluski.products.campaignes.backend.model.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

/**
 * AuthController is responsible for handling authentication-related HTTP
 * requests.
 * It provides endpoints for user authentication operations within the
 * application.
 */
@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final UserService userService;

    /**
     * Constructs an AuthController with a UserService.
     * 
     * @param userService The UserService to be used by this controller.
     */
    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Handles the login request for a user.
     * 
     * This method processes POST requests on the "/user/login" endpoint.
     * It attempts to authenticate a user based on the provided UserDto object.
     * 
     * @param userDto The UserDto containing the user's login credentials.
     * @return A ResponseEntity containing the authenticated UserDto object if
     *         authentication is successful,
     *         otherwise, it may return an appropriate error response.
     */
    @PostMapping("/user/login")
    public ResponseEntity<?> handleLoginRequest(@RequestBody UserDto userDto) {
        UserDto authenticatedUserObject = userService.getAuthenticatedUserObject(userDto);
        if (authenticatedUserObject != null) {
            String accessToken = userService.getAccessToken(authenticatedUserObject.getUsername());
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("userCredentials", authenticatedUserObject);
            responseBody.put("accessToken", accessToken);
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * Handles the request to refresh access tokens.
     * 
     * This method processes GET requests on the "/tokens/refresh" endpoint.
     * It attempts to refresh the user's access token based on the provided
     * HttpServletRequest.
     * 
     * @param request The HttpServletRequest containing the necessary information to
     *                refresh the access token.
     * @return A ResponseEntity containing the refreshed access token if the
     *         operation is successful,
     *         otherwise, it returns an UNAUTHORIZED status indicating the refresh
     *         operation failed.
     */
    @GetMapping("/tokens/refresh")
    public ResponseEntity<?> handleRefreshTokensRequest(HttpServletRequest request) {
        try {
            String refreshedAccessToken = userService.getRefreshedAccessToken(request);
            return ResponseEntity.status(HttpStatus.OK).body(refreshedAccessToken);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /*
     * Handles the logout request for a user.
     * 
     * This method processes GET requests on the "/user/logout" endpoint.
     * It attempts to log out the user based on the provided HttpServletRequest.
     * 
     * @param request The HttpServletRequest containing the necessary information to
     * log out the user.
     * 
     * @return A ResponseEntity indicating the outcome of the logout operation.
     * If the user is logged out correctly, it returns an OK status;
     * otherwise, it returns a FORBIDDEN status indicating the logout operation
     * failed.
     */
    @GetMapping("/user/logout")
    public ResponseEntity<?> handleLogoutRequest(HttpServletRequest request) {
        Boolean isUserLoggetOutCorrectly = userService.getIsUserLogoutCorrectly(request);
        return isUserLoggetOutCorrectly ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

}
