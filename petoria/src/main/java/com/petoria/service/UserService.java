package com.petoria.service;

import com.petoria.dto.UserDto;
import com.petoria.model.User;
import com.petoria.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDto createUser(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getUsername() + "_default"); // placeholder
        user.setBirthday(dto.getBirthday());
        userRepository.save(user);
        return dto;
    }
}
