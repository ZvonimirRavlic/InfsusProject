package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class Grad {
    @Id
    @Column(name = "gradid", nullable = false)
    private Integer id;

    @Column(name = "naziv", nullable = false, length = 256)
    private String naziv;

    @Column(name = "drzava", nullable = false, length = 256)
    private String drzava;

    @Column(name = "postanskibroj", nullable = false)
    private Integer postanskibroj;


    public Integer getPostanskibroj() {
        return postanskibroj;
    }

    public void setPostanskibroj(Integer postanskibroj) {
        this.postanskibroj = postanskibroj;
    }

    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
