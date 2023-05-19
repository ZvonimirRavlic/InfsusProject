package com.example.dto;

import java.util.Objects;

public class PredmetDto implements Comparable<PredmetDto> {
    private int predmetId;
    private String naziv;
    private int godina;

    private String nazivGodina;

    public int getPredmetId() {
        return predmetId;
    }

    public void setPredmetId(int predmetId) {
        this.predmetId = predmetId;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getGodina() {
        return godina;
    }

    public void setGodina(int godina) {
        this.godina = godina;
    }

    public String getNazivGodina() {
        return nazivGodina;
    }

    public void setNazivGodina(String nazivGodina) {
        this.nazivGodina = nazivGodina;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PredmetDto that = (PredmetDto) o;
        return predmetId == that.predmetId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(predmetId);
    }

    @Override
    public int compareTo(PredmetDto o) {
        return getNazivGodina().compareTo(o.getNazivGodina());
    }
}
