package com.example.simplebank.controller.security;

import com.example.simplebank.dto.security.request.AuthRequestDTO;
import com.example.simplebank.dto.security.request.RegisterRequestDTO;
import com.example.simplebank.dto.security.response.AuthResponseDTO;
import com.example.simplebank.service.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO dto) {
        return ResponseEntity.ok(authService.register(dto));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDTO> authenticate(@RequestBody AuthRequestDTO dto) {
        return ResponseEntity.ok(authService.authenticate(dto));
    }
}
