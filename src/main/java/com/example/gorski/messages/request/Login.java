package com.example.gorski.messages.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter

public class Login {
    @NotBlank
    @Size(min=3, max = 30)
    private String username;
    @NotBlank
    @Size(min=6, max = 30)
    private String password;
}
