package com.example.entity;

import jakarta.persistence.*;

import java.util.Objects;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ucenik ucenik = (Ucenik) o;
        return Objects.equals(id, ucenik.id) && Objects.equals(korisnik, ucenik.korisnik) && Objects.equals(razredid, ucenik.razredid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, korisnik, razredid);
    }
}