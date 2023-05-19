package com.example.entity;

import jakarta.persistence.*;

@Entity
public class Ucenik {
    @Id
    @Column(name = "korisnikid", nullable = false)
    private Integer id;

    @MapsId("name=korisnikid")
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "korisnikid", nullable = false)
    private Korisnik korisnik;
    @Column(name = "razredid", nullable = false)
    private Integer razredid;

    public Integer getRazredid() {
        return razredid;
    }

    public void setRazredid(Integer razredid) {
        this.razredid = razredid;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}