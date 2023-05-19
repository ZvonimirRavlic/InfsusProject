package com.example.entity;

import jakarta.persistence.*;

@Entity
public class Predaje {

    @EmbeddedId
    private PredajeId id;

    @MapsId("name=razredid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "razredid", nullable = false)
    private Razred razredid;

    @MapsId("name=predmetid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "predmetid", nullable = false)
    private Predmet predmetid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "korisnikid", nullable = false)
    private Korisnik korisnikId;

    public Korisnik getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(Korisnik korisnikId) {
        this.korisnikId = korisnikId;
    }

    public Predmet getPredmetid() {
        return predmetid;
    }

    public void setPredmetid(Predmet predmetid) {
        this.predmetid = predmetid;
    }

    public Razred getRazredid() {
        return razredid;
    }

    public void setRazredid(Razred razredid) {
        this.razredid = razredid;
    }

    public PredajeId getId() {
        return id;
    }

    public void setId(PredajeId id) {
        this.id = id;
    }
}
