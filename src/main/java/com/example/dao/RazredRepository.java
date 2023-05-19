package com.example.dao;

import com.example.entity.Razred;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RazredRepository extends JpaRepository<Razred, Integer> {
    @Override
    Optional<Razred> findById(Integer integer);
}