package com.example.dao;

import com.example.entity.Predmet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PredmetRepository  extends JpaRepository<Predmet, Integer> {
}
