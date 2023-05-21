package com.example.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.time.LocalDate;

@Entity
public class Ispit {

    @EmbeddedId
    private IspitId id;

    @MapsId("name=predmetid")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "predmetid", nullable = false)
    private Predmet predmetid;

    @Column(name = "napomena", length = 1000)
    private String napomena;

    @Column(name = "datum", nullable = false)
    private LocalDate datum;

    @Column(name = "vrsta", nullable = false, length = 256)
    private String vrsta;

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

    public Predmet getPredmetid() {
        return predmetid;
    }

    public void setPredmetid(Predmet predmetid) {
        this.predmetid = predmetid;
    }

    public IspitId getId() {
        return id;
    }

    public void setId(IspitId id) {
        this.id = id;
    }
}
