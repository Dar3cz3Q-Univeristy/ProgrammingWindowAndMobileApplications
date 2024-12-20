package com.example.lab5.repository;

import com.example.lab5.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RateRepository extends JpaRepository<Rate, UUID> {
}
