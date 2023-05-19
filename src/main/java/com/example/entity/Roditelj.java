package com.example.entity;

import jakarta.persistence.*;

@Entity
public class Roditelj {
    @Id
    @Column(name = "korisnikid", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "korisnikid", nullable = false)
    private Korisnik korisnik;

    @Column(name = "strucnasprema", nullable = false, length = 256)
    private String strucnasprema;

    @Column(name = "zanimanje", nullable = false, length = 256)
    private String zanimanje;


    public String getZanimanje() {
        return zanimanje;
    }

    public void setZanimanje(String zanimanje) {
        this.zanimanje = zanimanje;
    }

    public String getStrucnasprema() {
        return strucnasprema;
    }

    public void setStrucnasprema(String strucnasprema) {
        this.strucnasprema = strucnasprema;
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
