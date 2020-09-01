package com.nikodem.onlineshop.controllers;

import com.nikodem.onlineshop.domain.User;
import com.nikodem.onlineshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/myAccount")
public class UserAccountController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String getUserInfo(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((User) principal).getUsername();
        }
        else {
            username = principal.toString();
        }

        User currentUser = userRepository.findByUsername(username);

        System.out.println(currentUser.toString());

        model.addAttribute("currentUser", currentUser);

        return "myAccount";
    }
}
