package com.example.gorski.messages.request;

import com.example.gorski.domain.users.model.UserGender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class EditUser {
    private Long id;
    private String username;
    private UserGender userGender;
}
