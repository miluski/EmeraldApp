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

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

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

    @GetMapping("/user/logout")
    public ResponseEntity<?> handleLogoutRequest(HttpServletRequest request) {
        Boolean isUserLoggetOutCorrectly = userService.getIsUserLogoutCorrectly(request);
        return isUserLoggetOutCorrectly ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

}
