package com.petoria.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDto {
    private String usernameOrEmail;
    private String password;
}
