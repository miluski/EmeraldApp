package com.miluski.products.campaignes.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.miluski.products.campaignes.backend.model.dto.UserDto;
import com.miluski.products.campaignes.backend.model.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = { "http://localhost:5173/",
        "https://miluski.github.io/RecruitmentTask/" }, methods = { RequestMethod.GET,
                RequestMethod.POST }, allowCredentials = "true")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> handleLoginRequest(@RequestBody UserDto userDto, HttpServletRequest request,
            HttpServletResponse response) {
        System.out.println("It works");
        UserDto authenticatedUserObject = userService.getAuthenticatedUserObject(userDto);
        if (authenticatedUserObject != null) {
            System.out.println("It is here");
            userService.setSessionTokens(request, response, userDto.getUsername());
            return ResponseEntity.status(HttpStatus.OK).body(authenticatedUserObject);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/tokens/refresh")
    public ResponseEntity<?> handleRefreshTokensRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            userService.handleRefreshToken(request, response);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/user/logout")
    public ResponseEntity<?> handleLogoutRequest(HttpServletRequest request, HttpServletResponse response) {
        Boolean isUserLoggetOutCorrectly = userService.getIsUserLogoutCorrectly(request, response);
        return isUserLoggetOutCorrectly ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

}
