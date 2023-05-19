package com.example.dao;

import com.example.entity.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfesorRepository  extends JpaRepository<Profesor, Integer> {
}
