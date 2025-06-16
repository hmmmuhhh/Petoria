package com.petoria.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UserDto {
    private String username;
    private String email;
    private String usernameOrEmail;
    private String password;
    private LocalDate birthday;
}
