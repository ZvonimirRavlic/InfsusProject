package com.example.dto;

import java.time.LocalDate;

public class IspitUpdate {

    private Integer ispitId;
    private Integer predmetId;
    private String vrsta;
    private LocalDate datum;
    private String napomena;

    public Integer getIspitId() {
        return ispitId;
    }

    public void setIspitId(Integer ispitId) {
        this.ispitId = ispitId;
    }

    public Integer getPredmetId() {
        return predmetId;
    }

    public void setPredmetId(Integer predmetId) {
        this.predmetId = predmetId;
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
}


