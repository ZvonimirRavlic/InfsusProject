package com.example.mapper;

import com.example.dto.IspitDto;
import com.example.dto.OcjenaDto;
import com.example.dto.PredmetDto;
import com.example.dto.UcenikDto;
import com.example.entity.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class Mapper {

    public IspitDto map(final Ispit ispit, final List<Piše> ocjene, final List<Predaje> predmeti) {
        final IspitDto ispitDto = new IspitDto();
        ispitDto.setDatum(ispit.getDatum());
        ispitDto.setNapomena(ispit.getNapomena());
        ispitDto.setVrsta(ispit.getVrsta());
        ispitDto.setIspitId(ispit.getId());
        ispitDto.setOcjene(ocjene.stream().map(this::map).toList());
        List<PredmetDto> predmetiDto = new java.util.ArrayList<>(predmeti.stream().map(this::map).toList());
        Collections.swap(predmetiDto, 0, predmetiDto.indexOf(map(ispit.getPredmetid())));
        ispitDto.setPredmeti(predmetiDto);
        return ispitDto;
    }

    private OcjenaDto map(final Piše ocjena) {
        final OcjenaDto ocjenaDto = new OcjenaDto();
        ocjenaDto.setNapomena(ocjena.getNapomena());
        ocjenaDto.setOcjena(ocjena.getOcjena());
        ocjenaDto.setUcenik(map(ocjena.getKorisnikId()));
        return ocjenaDto;
    }

    private UcenikDto map(Ucenik ucenik) {
        final UcenikDto ucenikDto = new UcenikDto();
        ucenikDto.setKorisnikId(ucenik.getId());
        ucenikDto.setImePrezime(ucenik.getKorisnik().getIme() + " " + ucenik.getKorisnik().getPrezime());
        return ucenikDto;
    }

    private PredmetDto map(final Predaje predaje) {
        final Predmet predmet = predaje.getPredmetid();
        final PredmetDto predmetDto = new PredmetDto();
        predmetDto.setPredmetId(predmet.getId());
        predmetDto.setNaziv(predmet.getNaziv());
        predmetDto.setGodina(predmet.getGodina());
        predmetDto.setNazivGodina(predmet.getNaziv() + " " + predmet.getGodina());
        return predmetDto;
    }

    public PredmetDto map(final Predmet predmet) {
        final PredmetDto predmetDto = new PredmetDto();
        predmetDto.setPredmetId(predmet.getId());
        predmetDto.setNaziv(predmet.getNaziv());
        predmetDto.setGodina(predmet.getGodina());
        predmetDto.setNazivGodina(predmet.getNaziv() + " " + predmet.getGodina());
        return predmetDto;
    }
}
