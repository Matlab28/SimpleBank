package com.example.simplebank.repository;

import com.example.simplebank.entity.CreditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRepository extends JpaRepository<CreditEntity, Long> {
}
