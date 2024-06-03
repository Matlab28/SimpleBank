package com.example.simplebank.entity.security;


import com.example.simplebank.constant.Role;
import com.example.simplebank.dto.security.response.PersonResponseDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Setter
@Getter
public class PersonEntity implements Serializable, UserDetails {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_person_id")
    @SequenceGenerator(name = "gen_person_id", sequenceName = "seq_person_id", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "person_role")
    private Set<Integer> roles = new HashSet<>(Arrays.asList(Role.USER.getId()));

    public PersonEntity() {
        super();
    }

    public PersonEntity(Long id, String name, String email, String password, Set<Role> roles) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.setRoles(roles);
    }

    public PersonEntity(String name, String email, String password) {
        super();
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public PersonEntity(PersonResponseDto dto) {
        this(dto.getName(), dto.getEmail(), dto.getPassword());
        this.setId(dto.getId());
        this.setStringRoles(dto.getRoles());
    }


    public Set<Role> getRoles() {
        return roles.stream().map(r -> Role.fromId(r)).collect(Collectors.toSet());
    }


    public void setRoles(Set<Role> roles) {
        if (roles == null || roles.isEmpty())
            this.roles.clear();
        else
            this.roles = roles.stream().map(r -> r.getId()).collect(Collectors.toSet());
    }

    public void setStringRoles(Set<String> roles) {
        if (roles == null || roles.isEmpty())
            this.roles.clear();
        else
            this.roles = roles.stream().map(s -> Role.fromDescription(s).getId()).collect(Collectors.toSet());
    }

    public void addRole(Role role) {
        this.roles.add(role.getId());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority(Role.fromId(r).name()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "Person [id=" + id + ", name=" + name + ", email=" + email + ", roles=" + getRoles() + "]";
    }
}
