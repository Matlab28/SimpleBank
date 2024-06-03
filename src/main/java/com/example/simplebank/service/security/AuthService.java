package com.example.simplebank.service.security;

import com.example.simplebank.dto.security.request.AuthRequestDTO;
import com.example.simplebank.dto.security.request.RegisterRequestDTO;
import com.example.simplebank.dto.security.response.AuthResponseDTO;
import com.example.simplebank.entity.security.PersonEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PersonService personService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDTO register(RegisterRequestDTO dto) {

        PersonEntity person = new PersonEntity();
        person.setName(dto.getName());
        person.setEmail(dto.getEmail());
        person.setPassword(passwordEncoder.encode(dto.getPassword()));

        person = personService.create(person);

        return new AuthResponseDTO(jwtService.generateToken(person.getEmail()));
    }

    public AuthResponseDTO authenticate(AuthRequestDTO dto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getPassword()));

        final PersonEntity person = personService.findByEmail(dto.getEmail());
        return new AuthResponseDTO(jwtService.generateToken(person.getEmail()));
    }
}
