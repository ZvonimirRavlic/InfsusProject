package com.example.dao;

import com.example.entity.Piše;
import com.example.entity.PišeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PiseRepository extends JpaRepository<Piše, PišeId> {


    List<Piše> findByIspitIdAndPredmetId(Integer ispitId, Integer predmetId);

    @Query(
            value = """
            select p.*
            from piše p
                     join public.ispit i on i.ispitid = p.ispitid
                and i.predmetid = p.predmetid
            where datum =:datum and korisnikid =:korisnikId
            """,
            nativeQuery = true)
    List<Piše> findOcjeneNaDatumZaUcenika(LocalDate datum, Integer korisnikId);



}