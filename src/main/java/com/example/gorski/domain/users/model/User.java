package com.example.gorski.domain.users.model;

import com.example.gorski.domain.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "USERS")
public class User extends AbstractEntity {

    private String userName;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserGender userGender;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(String userName, String password, UserGender userGender) {
        this.userName = userName;
        this.password = password;
        this.userGender = userGender;
    }


}
