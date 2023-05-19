package com.example.dao;

import com.example.entity.Korisnik;
import com.example.entity.Ucenik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UcenikRepostory  extends JpaRepository<Ucenik, Integer> {
    @Override
    Optional<Ucenik> findById(Integer integer);

    @Query(value =
            """
                    select u.*
                    from public.predaje p join public.razred r on p.razredid = r.razredid
                    join public.ucenik u on r.razredid = u.razredid
                    where p.predmetid = :predmetId
                    """, nativeQuery = true)
    List<Ucenik> findUcenikeNaPredmetu(Integer predmetId);
}
