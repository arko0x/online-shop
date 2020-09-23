package com.nikodem.onlineshop.service;

import com.nikodem.onlineshop.domain.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

public interface UserService {
    User getCurrentUser();
    void save(User user);
    void refreshAuthenticationToken(User user);
}
