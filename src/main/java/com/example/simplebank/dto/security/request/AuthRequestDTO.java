package com.example.simplebank.dto.security.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthRequestDTO {
    private String email;
    private String password;
}