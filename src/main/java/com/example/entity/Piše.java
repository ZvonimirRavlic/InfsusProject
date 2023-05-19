package com.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "piše")
public class Piše {


    @EmbeddedId
    private PišeId id;

    @MapsId("name=korisnikid")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "korisnikid", nullable = false)
    private Ucenik korisnikId;

    @Column(name = "ispitid", insertable = false, updatable = false, nullable = false)
    private Integer ispitId;
    @Column(name = "predmetid", insertable = false, updatable = false, nullable = false)
    private Integer predmetId;

    @Column(name = "ocjena", nullable = false)
    private Integer ocjena;

    @Column(name = "napomena", length = 1000)
    private String napomena;

    public String getNapomena() {
        return napomena;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }

    public Integer getOcjena() {
        return ocjena;
    }

    public void setOcjena(Integer ocjena) {
        this.ocjena = ocjena;
    }


    public Ucenik getKorisnikId() {
        return korisnikId;
    }

    public Integer getIspitId() {
        return ispitId;
    }

    public void setIspitId(Integer ispitId) {
        this.ispitId = ispitId;
    }

    public Integer getPredmetId() {
        return predmetId;
    }

    public void setPredmetId(Integer predmetId) {
        this.predmetId = predmetId;
    }

    public void setKorisnikId(Ucenik korisnikId) {
        this.korisnikId = korisnikId;
    }

    public PišeId getId() {
        return id;
    }

    public void setId(PišeId id) {
        this.id = id;
    }
}