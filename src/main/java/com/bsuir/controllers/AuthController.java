package com.bsuir.controllers;

import com.bsuir.models.ERole;
import com.bsuir.models.Role;
import com.bsuir.models.User;
import com.bsuir.security.models.JwtRequest;
import com.bsuir.security.models.JwtResponse;
import com.bsuir.security.models.RefreshJwtRequest;
import com.bsuir.security.services.AuthService;
import com.bsuir.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.message.AuthException;
import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public JwtResponse login(@Valid @RequestBody JwtRequest authRequest) throws AuthException {
        return authService.login(authRequest);
    }

    @PostMapping("/register")
    public JwtResponse register(@Valid @RequestBody JwtRequest authRequest) throws AuthException {
        User user = new User(null, authRequest.getEmail(), authRequest.getPassword(), Set.of(new Role(ERole.ROLE_USER)));
        userService.save(user);
        return authService.login(authRequest);
    }

    @PostMapping("/token")
    public JwtResponse getToken(@Valid @RequestBody RefreshJwtRequest refreshJwtRequest) throws AuthException {
        return authService.getAccessToken(refreshJwtRequest.getRefreshToken());
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(@Valid @RequestBody RefreshJwtRequest refreshJwtRequest) throws AuthException {
        return authService.refresh(refreshJwtRequest.getRefreshToken());
    }

}
