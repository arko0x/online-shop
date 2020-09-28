package com.nikodem.onlineshop.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
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

    @NotNull
    @NotBlank
    @Pattern(regexp="(^$|[0-9]{9})", message = "Numer musi mieć 9 cyfr")
    private String phoneNumber;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(@NotNull @NotBlank String username, @NotNull @NotBlank String password, @NotNull @NotBlank String city, @NotNull @NotBlank @Pattern(regexp = "[0-9][0-9]-[0-9][0-9][0-9]") String zip, @NotNull @NotBlank String state, @NotNull @NotBlank String street, @NotNull @NotBlank String houseNumber, @NotNull @NotBlank @Pattern(regexp="(^$|[0-9]{9})", message = "Numer musi mieć 9 cyfr") String phoneNumber) {
        this.username = username;
        this.password = password;
        this.city = city;
        this.zip = zip;
        this.state = state;
        this.street = street;
        this.houseNumber = houseNumber;
        this.phoneNumber = phoneNumber;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", city='" + city + '\'' +
                ", zip='" + zip + '\'' +
                ", state='" + state + '\'' +
                ", street='" + street + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
