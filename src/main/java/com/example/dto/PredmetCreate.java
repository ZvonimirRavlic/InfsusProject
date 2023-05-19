package com.example.dto;

import java.util.Objects;

public class PredmetCreate {

    private String naziv;
    private Integer godina;

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


}
