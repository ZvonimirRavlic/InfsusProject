package com.example.entity;

import jakarta.persistence.*;

@Entity
public class Profesor {


    @EmbeddedId
    private ProfesorId id;

    @MapsId("name=korisnikid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "korisnikid", nullable = false)
    private Korisnik korisnikId;

    @Column(name = "zavrsenifakultet", nullable = false, length = 256)
    private String zavrsenifakultet;

    public String getZavrsenifakultet() {
        return zavrsenifakultet;
    }

    public void setZavrsenifakultet(String zavrsenifakultet) {
        this.zavrsenifakultet = zavrsenifakultet;
    }

    public Korisnik getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(Korisnik korisnikId) {
        this.korisnikId = korisnikId;
    }

    public ProfesorId getId() {
        return id;
    }

    public void setId(ProfesorId id) {
        this.id = id;
    }
}
