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

    public IspitDto map(final Ispit ispit, final List<Piše> ocjene, final List<Predmet> predmeti, List<Ucenik> ucenici) {
        final IspitDto ispitDto = new IspitDto();
        ispitDto.setDatum(ispit.getDatum());
        ispitDto.setNapomena(ispit.getNapomena());
        ispitDto.setVrsta(ispit.getVrsta());
        ispitDto.setIspitId(ispit.getId());
        ispitDto.setOcjene(ocjene.stream().map(ocjena -> map(ocjena, ucenici)).toList());
        List<PredmetDto> predmetiDto = new java.util.ArrayList<>(predmeti.stream().map(this::map).toList());
        Collections.sort(predmetiDto);
        final int indexOfPredmet = predmetiDto.indexOf(map(ispit.getPredmetid()));
        final PredmetDto predmetDto = predmetiDto.get(indexOfPredmet);
        predmetiDto.remove(predmetDto);
        predmetiDto.add(0, predmetDto);
        ispitDto.setPredmeti(predmetiDto);
        final List<UcenikDto> ucenikDtos = new java.util.ArrayList<>(ucenici.stream().map(this::map).toList());
        Collections.sort(ucenikDtos);
        ispitDto.setUcenici(ucenikDtos);
        return ispitDto;
    }


    private OcjenaDto map(final Piše ocjena, List<Ucenik> ucenici) {
        final OcjenaDto ocjenaDto = new OcjenaDto();
        ocjenaDto.setNapomena(ocjena.getNapomena());
        ocjenaDto.setOcjena(ocjena.getOcjena());
        List<UcenikDto> uceniciDto = new java.util.ArrayList<>(ucenici.stream().map(this::map).toList());
        final int indexOfPredmet = uceniciDto.indexOf(map(ocjena.getKorisnikId()));
        final UcenikDto ucenikDto = uceniciDto.get(indexOfPredmet);
        uceniciDto.remove(ucenikDto);
        uceniciDto.add(0, ucenikDto);
        ocjenaDto.setUcenici(uceniciDto);
        Collections.swap(uceniciDto, 0, uceniciDto.indexOf(map(ocjena.getKorisnikId())));
        ocjenaDto.setUcenici(uceniciDto);
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
