package com.nikodem.onlineshop.controllers;

import com.nikodem.onlineshop.domain.RegisterForm;
import com.nikodem.onlineshop.domain.User;
import com.nikodem.onlineshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("registerForm", new RegisterForm());

        return "register";
    }

    @PostMapping
    public String register(@ModelAttribute RegisterForm registerForm) {
        User user = registerForm.toUser(passwordEncoder);

        userRepository.save(user);

        return "redirect:/login";
    }
}
