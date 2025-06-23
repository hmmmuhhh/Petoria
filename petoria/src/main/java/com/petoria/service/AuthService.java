package com.petoria.service;

import com.petoria.dto.UserDto;
import com.petoria.util.CredsValidator;
import com.petoria.util.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;

    public void createUser(UserDto user) {
        if (!CredsValidator.validate(user)) {
            throw new IllegalArgumentException("Invalid user credentials.");
        }

        if (userService.findByUsernameOrEmail(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username or email already in use.");
        }
        userService.createUser(user);
    }

    public String authenticateUser(UserDto user) {
        var login = user.getUsernameOrEmail();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login, user.getPassword())
        );
        String username = authentication.getName();
        return tokenUtils.generateToken(username);
    }
}
