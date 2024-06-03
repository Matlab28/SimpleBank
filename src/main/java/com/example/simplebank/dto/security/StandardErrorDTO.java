package com.example.simplebank.dto.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class StandardErrorDTO {

    private Integer status;
    private String error;
    private String message;
    private String path;

    public StandardErrorDTO(HttpStatus status, Throwable ex, HttpServletRequest request) {
        this.status = status.value();
        this.error = ex.getClass().getSimpleName();
        this.message = ex.getMessage();
        this.path = request.getRequestURI();
    }
}
