package com.nikodem.onlineshop.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE,force = true)
public class User implements UserDetails{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(@NotNull @NotBlank String username, @NotNull @NotBlank String password, @NotNull @NotBlank String city, @NotNull @NotBlank String zip, @NotNull @NotBlank String state, @NotNull @NotBlank String street, @NotNull @NotBlank String houseNumber) {
        this.username = username;
        this.password = password;
        this.city = city;
        this.zip = zip;
        this.state = state;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    @OneToMany(mappedBy = "user")
    private List<Offer> offers;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
