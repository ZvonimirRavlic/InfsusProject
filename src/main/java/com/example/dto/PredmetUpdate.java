package com.example.dto;

import java.util.Objects;

public class PredmetUpdate {

    private Integer predmetId;
    private String naziv;
    private Integer godina;

    public Integer getPredmetId() {
        return predmetId;
    }

    public void setPredmetId(Integer predmetId) {
        this.predmetId = predmetId;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Integer getGodina() {
        return godina;
    }

    public void setGodina(Integer godina) {
        this.godina = godina;
    }

    @Override
    public String toString() {
        return "PredmetUpdate{" +
                "predmetId=" + predmetId +
                ", naziv='" + naziv + '\'' +
                ", godina=" + godina +
                '}';
    }
}
