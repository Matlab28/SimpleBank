package com.example.simplebank.service.security;

import com.example.simplebank.constant.Role;
import com.example.simplebank.dto.security.response.PersonResponseDto;
import com.example.simplebank.entity.security.PersonEntity;
import com.example.simplebank.exception.DuplicationException;
import com.example.simplebank.exception.NotFoundException;
import com.example.simplebank.repository.security.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository repository;

    public PersonEntity findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException("Person not found: " + id));
    }

    public PersonEntity findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Person not found: " + email));
    }

    public List<PersonEntity> findAll() {
        return repository.findAll();
    }

    public PersonEntity create(PersonEntity person) {
        person.setId(null);
        person.addRole(Role.USER);
        checkEmailDuplication(person);
        return repository.save(person);
    }

    public PersonResponseDto create(PersonResponseDto dto) {
        return new PersonResponseDto(create(new PersonEntity(dto)));
    }

    public PersonEntity update(PersonEntity person) {
        checkEmailDuplication(person);
        PersonEntity p = findById(person.getId());
        p.setName(person.getName());
        p.setEmail(person.getEmail());
        p.setRoles(person.getRoles());
        return repository.save(p);
    }

    public void delete(Long id) {
        final PersonEntity p = findById(id);
        repository.delete(p);
    }

    private void checkEmailDuplication(PersonEntity person) {
        final String email = person.getEmail();
        if (email != null && email.length() > 0) {
            final Long id = person.getId();
            final PersonEntity p = repository.findByEmail(email).orElse(null);
            if (p != null && Objects.equals(p.getEmail(), email) && !Objects.equals(p.getId(), id)) {
                throw new DuplicationException("Email duplication: " + email);
            }
        }
    }
}
