package com.nikodem.onlineshop.controllers;

import com.nikodem.onlineshop.domain.User;
import com.nikodem.onlineshop.repositories.UserRepository;
import com.nikodem.onlineshop.validation.UsernameValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/myAccount")
@SessionAttributes({"currentUser", "user"})
public class UserAccountController {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UsernameValidator usernameValidator;

    @Autowired
    public UserAccountController(UserRepository userRepository, PasswordEncoder passwordEncoder, UsernameValidator usernameValidator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.usernameValidator = usernameValidator;
    }

    @GetMapping
    public String getUserInfo(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal instanceof UserDetails ? ((User) principal).getUsername() : principal.toString();

        User currentUser = userRepository.findByUsername(username);

        model.addAttribute("currentUser", currentUser);

        return "myAccount";
    }

    @GetMapping("/edit")
    public String getEditPage(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal instanceof UserDetails ? ((User) principal).getUsername() : principal.toString();

        User currentUser = userRepository.findByUsername(username);
        User user = userRepository.findByUsername(username);

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("user", user);

        return "edit";
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT}, value = "/edit")
    public String editData(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, SessionStatus sessionStatus, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal instanceof UserDetails ? ((User) principal).getUsername() : principal.toString();

        if (!username.equals(user.getUsername())) usernameValidator.validate(user, bindingResult);

        User currentUser = userRepository.findByUsername(username);

        if (bindingResult.hasErrors()) {
            model.addAttribute("currentUser", currentUser);
            return "edit";
        }

        userRepository.save(user);
        refreshAuthenticationToken(user);
        sessionStatus.setComplete();

        return "index";
    }

    private void refreshAuthenticationToken(User user) {
        Collection<SimpleGrantedAuthority> nowAuthorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder
                .getContext().getAuthentication().getAuthorities();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(),
                passwordEncoder.encode(user.getPassword()), nowAuthorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
