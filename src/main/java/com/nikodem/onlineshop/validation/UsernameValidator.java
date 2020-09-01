package com.nikodem.onlineshop.validation;

import com.nikodem.onlineshop.domain.User;
import com.nikodem.onlineshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UsernameValidator implements Validator {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass == User.class;
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        if (userRepository.findByUsername(user.getUsername()) != null) errors.rejectValue("username", "Username " + user.getUsername() + " is already in database");
    }
}
