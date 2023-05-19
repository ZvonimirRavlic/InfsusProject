package com.example.dto;


import com.example.entity.IspitId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IspitDto {

    public IspitId ispitId;
    public String vrsta;
    public LocalDate datum;
    public String napomena;
    public List<PredmetDto> predmeti;
    public List<OcjenaDto> ocjene;
    public List<UcenikDto> ucenici;


    public IspitId getIspitId() {
        return ispitId;
    }

    public void setIspitId(IspitId ispitId) {
        this.ispitId = ispitId;
    }

    public String getVrsta() {
        return vrsta;
    }

    public void setVrsta(String vrsta) {
        this.vrsta = vrsta;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String getNapomena() {
        return napomena;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }

    public List<PredmetDto> getPredmeti() {
        return predmeti;
    }

    public void setPredmeti(List<PredmetDto> predmeti) {
        this.predmeti = predmeti;
    }

    public List<OcjenaDto> getOcjene() {
        return ocjene;
    }

    public void setOcjene(List<OcjenaDto> ocjene) {
        this.ocjene = ocjene;
    }

    public List<UcenikDto> getUcenici() {
        return ucenici;
    }

    public void setUcenici(List<UcenikDto> ucenici) {
        this.ucenici = ucenici;
    }
}
