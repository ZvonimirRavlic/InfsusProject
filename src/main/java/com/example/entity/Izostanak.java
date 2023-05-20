package com.example.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
public class Izostanak {
    @Id
    @Column(name = "izostanakid", nullable = false)
    private Integer id;

    @Column(name = "datum", nullable = false)
    private LocalDate datum;

    @Column(name = "vrsta", nullable = false, length = 256)
    private String vrsta;

    @Column(name = "napomena", length = 1000)
    private String napomena;

    @Column(name = "napomenaroditelja", length = 1000)
    private String napomenaroditelja;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "korisnikid", nullable = false)
    private Ucenik korisnikId;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "predmetid", nullable = false)
    private Predmet predmetid;

    public Predmet getPredmetid() {
        return predmetid;
    }

    public void setPredmetid(Predmet predmetid) {
        this.predmetid = predmetid;
    }

    public Ucenik getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(Ucenik korisnikId) {
        this.korisnikId = korisnikId;
    }

    public String getNapomenaroditelja() {
        return napomenaroditelja;
    }

    public void setNapomenaroditelja(String napomenaroditelja) {
        this.napomenaroditelja = napomenaroditelja;
    }

    public String getNapomena() {
        return napomena;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
