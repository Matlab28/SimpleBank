package com.example.simplebank.dto.security.request;

import com.example.simplebank.constant.Role;
import com.example.simplebank.entity.security.PersonEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
public class PersonRequestDto {
    private String email;
    private String password;
    private String passConfirm;
    private Set<String> roles = new HashSet<>();

    public PersonRequestDto() {
        super();
    }

    public PersonRequestDto(Long id, String name, String email, Set<String> roles) {
        super();
        this.email = email;
        this.roles = roles;
    }

    public PersonRequestDto(PersonEntity person) {
        super();
        this.email = person.getEmail();
        this.setRoles(person.getRoles());
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles.stream().map(r -> r.getDescription()).collect(Collectors.toSet());
    }
}
