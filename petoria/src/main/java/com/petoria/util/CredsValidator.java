package com.petoria.util;

import com.petoria.dto.UserDto;
import lombok.experimental.UtilityClass;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

@UtilityClass
public class CredsValidator {

    private static final EmailValidator EMAIL_VALIDATOR = EmailValidator.getInstance();

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[\\w.@$!%*#?&-]{5,20}$");

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s]).{5,}$"
    );

    public boolean isValidUsername(String username) {
        return username != null && USERNAME_PATTERN.matcher(username).matches();
    }

    public boolean isValidEmail(String email) {
        return email != null && EMAIL_VALIDATOR.isValid(email);
    }

    public boolean isValidPassword(String password) {
        return password != null && PASSWORD_PATTERN.matcher(password).matches();
    }

    public boolean isOldEnough(LocalDate birthday) {
        return birthday != null && Period.between(birthday, LocalDate.now()).getYears() >= 13;
    }

    public static boolean validate(UserDto user) {
        return isValidUsername(user.getUsername()) &&
                isValidEmail(user.getEmail()) &&
                isValidPassword(user.getPassword()) &&
                isOldEnough(user.getBirthday());
    }

    public static String validateAll(UserDto user) {
        if (!isValidUsername(user.getUsername())) return "Invalid username.";
        if (!isValidEmail(user.getEmail())) return "Invalid email.";
        if (!isValidPassword(user.getPassword())) return "Weak password.";
        if (!isOldEnough(user.getBirthday())) return "User must be at least 13 years old.";
        return null;
    }
}