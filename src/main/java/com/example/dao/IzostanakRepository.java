package com.example.dao;

import com.example.entity.Izostanak;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface IzostanakRepository extends JpaRepository<Izostanak, Integer> {
    boolean existsByDatumAndKorisnikId_IdAndPredmetid_Id(LocalDate datum, Integer korisnikId, Integer predmetId);


}