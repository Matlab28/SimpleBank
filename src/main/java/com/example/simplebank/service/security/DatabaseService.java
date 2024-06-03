package com.example.simplebank.service.security;

import com.example.simplebank.constant.Role;
import com.example.simplebank.entity.security.PersonEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DatabaseService {
    private final PersonService personService;
    private final PasswordEncoder passwordEncoder;

    public void initializeDatabase() {

        System.out.println("Initializing database...");

        final PersonEntity user1 = new PersonEntity("Emma", "emma@mail.com", passwordEncoder.encode("111"));
        final PersonEntity user2 = new PersonEntity("Jhon", "jhon@mail.com", passwordEncoder.encode("222"));
        final PersonEntity admin = new PersonEntity("Anna", "anna@mail.com", passwordEncoder.encode("333"));

        admin.addRole(Role.ADMIN);

        System.out.println(personService.create(user1));
        System.out.println(personService.create(user2));
        System.out.println(personService.create(admin));

        System.out.println("Database initialized!");
    }
}