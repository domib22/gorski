package com.example.gorski.services;

import com.example.gorski.domain.users.model.User;
import com.example.gorski.domain.users.model.UserGender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter

public class UserPrinciple implements UserDetails {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String username;

    @JsonIgnore
    private String password;

    private UserGender userGender;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrinciple(Long id, String username, UserGender userGender, String password,
                         Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.userGender = userGender;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrinciple build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName().name())
        ).collect(Collectors.toList());

        return new UserPrinciple(
                user.getId(),
                user.getUserName(),
                user.getUserGender(),
                user.getPassword(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        UserPrinciple user = (UserPrinciple) object;
        return Objects.equals(id, user.id);
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}