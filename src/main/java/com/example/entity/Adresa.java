package com.example.entity;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Adresa {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gradid")
    private Grad gradid;

    @Id
    @Column(name = "adresaid", nullable = false)
    private Integer id;

    @Column(name = "nazivulice", nullable = false, length = 256)
    private String nazivulice;

    @Column(name = "kucnibroj", nullable = false)
    private Integer kucnibroj;


    public Integer getKucnibroj() {
        return kucnibroj;
    }

    public void setKucnibroj(Integer kucnibroj) {
        this.kucnibroj = kucnibroj;
    }

    public String getNazivulice() {
        return nazivulice;
    }

    public void setNazivulice(String nazivulice) {
        this.nazivulice = nazivulice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Grad getGradid() {
        return gradid;
    }

    public void setGradid(Grad gradid) {
        this.gradid = gradid;
    }
}
