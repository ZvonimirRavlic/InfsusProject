package com.example.dto;

import com.example.entity.IspitId;

import java.time.LocalDate;

public class IspitiResp {

    public IspitId ispitId;
    public String vrsta;
    public LocalDate datum;
    public String napomena;
    public String nazivGodina;


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

    public String getNazivGodina() {
        return nazivGodina;
    }

    public void setNazivGodina(String nazivGodina) {
        this.nazivGodina = nazivGodina;
    }

}
