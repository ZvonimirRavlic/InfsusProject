package com.example.dao;

import com.example.entity.Predaje;
import com.example.entity.PredajeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PredajeRepository extends JpaRepository<Predaje, PredajeId> {
    List<Predaje> findByKorisnikId_Id(Integer id);
}