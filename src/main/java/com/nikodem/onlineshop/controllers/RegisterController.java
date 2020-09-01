package com.nikodem.onlineshop.controllers;

import com.nikodem.onlineshop.domain.RegisterForm;
import com.nikodem.onlineshop.domain.User;
import com.nikodem.onlineshop.repositories.UserRepository;
import com.nikodem.onlineshop.validation.UsernameValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UsernameValidator usernameValidator;

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("registerForm", new RegisterForm());

        return "register";
    }

    @PostMapping
    public String register(@ModelAttribute @Valid RegisterForm registerForm, BindingResult bindingResult, Model model) {
        User user = registerForm.toUser(passwordEncoder);

        usernameValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) return "register";

        userRepository.save(user);

        return "redirect:/login";
    }
}
