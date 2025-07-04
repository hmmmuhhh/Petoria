package com.petoria.service;

import com.petoria.dto.UserDto;
import com.petoria.dto.UserLoginDto;
import com.petoria.model.User;
import com.petoria.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(UserDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setBirthday(dto.getBirthday());
        user.setProfilePicUrl("/img/default-pfp-petoria.png");
        userRepository.save(user);
    }

    public User login(UserLoginDto dto) {
        return userRepository.findByEmailOrUsername(dto.getUsernameOrEmail(), dto.getUsernameOrEmail())
                .filter(user -> passwordEncoder.matches(dto.getPassword(), user.getPassword()))
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
    }

    public Optional<User> findByUsernameOrEmail(String input) {
        return userRepository.findByEmailOrUsername(input, input);
    }

}
