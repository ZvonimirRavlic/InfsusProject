package com.example.dao;

import com.example.entity.Grad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradRepository extends JpaRepository<Grad, Integer> {
}