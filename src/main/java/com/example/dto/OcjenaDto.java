package com.example.dto;

public class OcjenaDto {
    public int ocjena;
    public String napomena;
    public UcenikDto ucenik;

    public int getOcjena() {
        return ocjena;
    }

    public void setOcjena(int ocjena) {
        this.ocjena = ocjena;
    }

    public String getNapomena() {
        return napomena;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }

    public UcenikDto getUcenik() {
        return ucenik;
    }

    public void setUcenik(UcenikDto ucenik) {
        this.ucenik = ucenik;
    }
}
