package com.example.gorski.messages.request;

import com.example.gorski.domain.users.UserGender;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter

public class Registration {
    @NotBlank
    @Size(min = 3, max = 30)
    private String username;
    @NotBlank
    @Size(min = 6, max = 30)
    private String password;
    private UserGender userGender;
    private Set<String> role;
}
