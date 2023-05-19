package com.example.dto;

import java.util.List;

public class OcjenaDto {
    public int ocjena;
    public String napomena;
    public List<UcenikDto> ucenici;

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

    public List<UcenikDto> getUcenici() {
        return ucenici;
    }

    public void setUcenici(List<UcenikDto> ucenici) {
        this.ucenici = ucenici;
    }
}
