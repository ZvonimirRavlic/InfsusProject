package com.example.dao;

import com.example.entity.Ispit;
import com.example.entity.IspitId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IspitRepository extends JpaRepository<Ispit, IspitId> {
    Optional<Ispit> findFirstById_IspitIdLessThanAndId_PredmetIdOrderById_IspitIdDesc(Integer ispitId, Integer predmetId);
    Optional<Ispit> findFirstById_IspitIdGreaterThanAndId_PredmetIdOrderById_IspitIdAsc(Integer ispitId, Integer predmetId);
    @Override
    Optional<Ispit> findById(IspitId ispitId);
    @Query(
            value = "SELECT nextval('predmet_id_seq') ",
            nativeQuery = true)
    Integer getNextId();

    @Override
    void deleteById(IspitId ispitId);
}