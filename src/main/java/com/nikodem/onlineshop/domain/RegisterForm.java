package com.nikodem.onlineshop.domain;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.*;

@Data
public class RegisterForm {
    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @NotBlank
    @Size(min = 8)
    private String password;

    @NotNull
    @NotBlank
    private String city;

    @NotNull
    @NotBlank
    @Pattern(regexp = "[0-9][0-9]-[0-9][0-9][0-9]")
    private String zip;

    @NotNull
    @NotBlank
    private String state;

    @NotNull
    @NotBlank
    private String street;

    @NotNull
    @NotBlank
    private String houseNumber;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(username, passwordEncoder.encode(password), city, zip, state, street, houseNumber);
    }

}
