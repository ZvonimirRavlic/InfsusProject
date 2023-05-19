package com.example.service;

import com.example.dao.PredmetRepository;
import com.example.dto.PredmetCreate;
import com.example.dto.PredmetDto;
import com.example.dto.PredmetUpdate;
import com.example.entity.Predmet;
import com.example.mapper.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PredmetServiceImpl implements PredmetService {
    private final PredmetRepository predmetRepository;
    private final Mapper mapper;

    public PredmetServiceImpl(PredmetRepository predmetRepository,
                              Mapper mapper) {
        this.predmetRepository = predmetRepository;
        this.mapper = mapper;
    }

    @Override
    public List<PredmetDto> getPredmete() {
        final List<Predmet> predmeti = predmetRepository.findAll();
        return predmeti.stream()
                .map((mapper::map))
                .toList();
    }

    @Override
    public List<PredmetDto> deletePredmet(Integer predmetId) {
        predmetRepository.deleteById(predmetId);
        return getPredmete();
    }

    @Override
    public List<PredmetDto> createPredmet(PredmetCreate predmetCreate) {
        if (predmetCreate.getGodina() == null
                || predmetCreate.getNaziv() == null) {
            throw new RuntimeException("Nisu popunjena sva polja za kreiranje predmeta");
        }
        final Predmet predmet = new Predmet();
        predmet.setNaziv(predmetCreate.getNaziv());
        predmet.setGodina(predmetCreate.getGodina());
        predmetRepository.saveAndFlush(predmet);
        return getPredmete();
    }

    @Override
    public List<PredmetDto> updatePredmet(PredmetUpdate predmetUpdate) {
        if (predmetUpdate.getGodina() == null
                || predmetUpdate.getNaziv() == null
                || predmetUpdate.getPredmetId() == null) {
            throw new RuntimeException("Nisu popunjena sva polja za azuriranje predmeta");
        }
        final Predmet predmet = new Predmet();
        predmet.setId(predmetUpdate.getPredmetId());
        predmet.setNaziv(predmetUpdate.getNaziv());
        predmet.setGodina(predmetUpdate.getGodina());
        predmetRepository.saveAndFlush(predmet);
        return getPredmete();
    }
}
