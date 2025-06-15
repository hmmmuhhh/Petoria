package com.petoria.service;

import com.petoria.dto.UserDto;
import com.petoria.model.User;
import com.petoria.util.CredentialsValidator;
import com.petoria.util.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;
    private final PasswordEncoder passwordEncoder;

    public void createUser(UserDto user) {
        if (!CredentialsValidator.validate(user)) {
            throw new IllegalArgumentException("Invalid user credentials.");
        }
        var encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userService.createUser(user);
    }

    public String authenticateUser(UserDto user) {
        var login = user.getUsername() != null ? user.getUsername() : user.getEmail();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login, user.getPassword())
        );
        User authUser = (User) authentication.getPrincipal();
        return tokenUtils.generateToken(authUser.getUsername());
    }
}
