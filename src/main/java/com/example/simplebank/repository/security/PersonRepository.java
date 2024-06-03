package com.example.simplebank.repository.security;


import com.example.simplebank.entity.security.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
    Optional<PersonEntity> findByEmail(String email);
}
