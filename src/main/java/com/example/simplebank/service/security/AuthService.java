package com.example.simplebank.service.security;

import com.example.simplebank.dto.request.ConfirmRequestDto;
import com.example.simplebank.dto.security.request.AuthRequestDTO;
import com.example.simplebank.dto.security.request.RegisterRequestDTO;
import com.example.simplebank.dto.security.response.AuthResponseDTO;
import com.example.simplebank.entity.security.PersonEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
@Setter
@Getter
@RequiredArgsConstructor
public class AuthService {
    private final PersonService personService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final JavaMailSender javaMailSender;
    private Random random = new Random();
    private Integer confirmation = random.nextInt(1000, 9999);

    public AuthResponseDTO register(RegisterRequestDTO dto) {
        if (!dto.getEmail().matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$")
                && !dto.getEmail().matches("^[a-zA-Z0-9._%+-]+@icloud\\.com$")
                && !dto.getEmail().matches("^[\\w._%+-]+@yahoo\\.com$")
                && !dto.getEmail().matches("^[a-zA-Z0-9._%+-]+@yandex\\.com$")) {
            log.error("User's email is invalid.");
            throw new RuntimeException("Student's email is invalid.");
        } else if (!dto.getPassword().matches(".*[A-Z].*")) {
            log.error("User's pass doesn't contain an uppercase.");
            throw new RuntimeException("User's pass doesn't contain an uppercase.");
        } else if (!dto.getPassword().matches(".*[a-z].*")) {
            log.error("User's pass doesn't contain a lowercase.");
            throw new RuntimeException("User's pass doesn't contain a lowercase.");
        } else if (!dto.getPassword().matches(".*\\d+.*")) {
            log.error("User's pass doesn't contain a number.");
            throw new RuntimeException("User's pass doesn't contain a number.");
        } else if (dto.getPassword().length() != 8) {
            log.error("User's pass length less than 8.");
            throw new RuntimeException("User's pass length less than 8.");
        }

        if (!dto.getPassword().equals(dto.getPassConfirm())) {
            log.error("Invalid password confirmation...");
            throw new RuntimeException("Invalid password confirmation...");
        }

        sendEmail(dto);

        PersonEntity person = new PersonEntity();
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

    public void sendEmail(RegisterRequestDTO dto) {
        if (dto.getEmail() == null) {
            log.error("Email address is null from student. Cannot send an email.");
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        String subject = "Email Confirmation";
        String text = "Hi, thank you for your registration!" +
                "\n\nThis is your confirmation code : " + getConfirmation() + "\n\nDo not share it.";

        message.setFrom("matlab.abbaszada@gmail.com");
        message.setTo(dto.getEmail());
        message.setText(text);
        message.setSubject(subject);
        javaMailSender.send(message);
        log.info("Message sent to - " + dto.getEmail());
    }

    public String confirm(ConfirmRequestDto dto) {
        if (Integer.parseInt(dto.getConfirmation()) == getConfirmation()) {
            return "Email confirmed successfully!\nThank you for your registration!";
        }
        return "Invalid information code...";
    }
}
