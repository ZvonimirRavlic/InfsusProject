package com.example.dto;

public class OcjenaCreate {

    private Integer ispitId;
    private Integer predmetId;
    private Integer korisnikId;
    private Integer ocjena;
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

    public Integer getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(Integer korisnikId) {
        this.korisnikId = korisnikId;
    }

    public Integer getOcjena() {
        return ocjena;
    }

    public void setOcjena(Integer ocjena) {
        this.ocjena = ocjena;
    }

    public String getNapomena() {
        return napomena;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }
}
