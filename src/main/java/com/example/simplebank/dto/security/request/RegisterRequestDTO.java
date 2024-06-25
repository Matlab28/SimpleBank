package com.example.simplebank.dto.security.request;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterRequestDTO {
    private String email;
    private String password;
    private String passConfirm;
}
