package com.example.dao;

import com.example.entity.Ucenik;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UcenikRepostory  extends JpaRepository<Ucenik, Integer> {
    @Override
    Optional<Ucenik> findById(Integer integer);
}
