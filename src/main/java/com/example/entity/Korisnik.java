package com.example.entity;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Korisnik {

    @Id
    @Column(name = "korisnikid", nullable = false)
    private Integer id;

    @Column(name = "username", nullable = false, length = 256)
    private String username;

    @Column(name = "sifra", nullable = false, length = 256)
    private String sifra;

    @Column(name = "ime", nullable = false, length = 256)
    private String ime;

    @Column(name = "prezime", nullable = false, length = 256)
    private String prezime;

    @Column(name = "oib", nullable = false, length = 11)
    private String oib;

    @Column(name = "brojmobitela", length = 256)
    private String brojmobitela;

    public String getBrojmobitela() {
        return brojmobitela;
    }

    public void setBrojmobitela(String brojmobitela) {
        this.brojmobitela = brojmobitela;
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
