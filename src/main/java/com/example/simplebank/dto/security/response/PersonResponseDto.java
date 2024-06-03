package com.example.simplebank.dto.security.response;


import com.example.simplebank.constant.Role;
import com.example.simplebank.entity.security.PersonEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
@Setter
public class PersonResponseDto {

    private Long id;
    private String name;
    private String email;
    private String password;
    private Set<String> roles = new HashSet<>();

    public PersonResponseDto() {
        super();
    }

    public PersonResponseDto(Long id, String name, String email, Set<String> roles) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
        this.roles = roles;
    }

    public PersonResponseDto(PersonEntity person) {
        super();
        this.id = person.getId();
        this.name = person.getName();
        this.email = person.getEmail();
        this.setRoles(person.getRoles());
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles.stream().map(r -> r.getDescription()).collect(Collectors.toSet());
    }
}