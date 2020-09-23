package com.nikodem.onlineshop.controllers;

import com.nikodem.onlineshop.domain.User;
import com.nikodem.onlineshop.service.UserService;
import com.nikodem.onlineshop.validation.UsernameValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Controller
@RequestMapping("/myAccount")
@SessionAttributes({"currentUser", "user"})
public class UserAccountController {
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final UsernameValidator usernameValidator;

    @Autowired
    public UserAccountController(UserService userService, PasswordEncoder passwordEncoder, UsernameValidator usernameValidator) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.usernameValidator = usernameValidator;
    }

    @GetMapping
    public String getUserInfo(Model model) {
        User currentUser = userService.getCurrentUser();

        model.addAttribute("currentUser", currentUser);

        return "myAccount";
    }

    @GetMapping("/edit")
    public String getEditPage(Model model) {
        User currentUser = userService.getCurrentUser();
        User user = userService.getCurrentUser();

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("user", user);

        return "edit";
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT}, value = "/edit")
    public String editData(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, SessionStatus sessionStatus, Model model) {
        if (!userService.getCurrentUser().getUsername().equals(user.getUsername())) usernameValidator.validate(user, bindingResult);

        User currentUser = userService.getCurrentUser();

        if (bindingResult.hasErrors()) {
            model.addAttribute("currentUser", currentUser);
            return "edit";
        }

        userService.save(user);
        userService.refreshAuthenticationToken(user);
        sessionStatus.setComplete();

        return "index";
    }
}
