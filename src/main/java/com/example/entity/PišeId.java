package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PišeId implements Serializable {
    private static final long serialVersionUID = 5140004304106847835L;
    @Column(name = "korisnikid", nullable = false)
    private Integer korisnikId;

    @Column(name = "ispitid", nullable = false)
    private Integer ispitId;

    @Column(name = "predmetid", nullable = false)
    private Integer predmetId;

    public Integer getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(Integer korisnikId) {
        this.korisnikId = korisnikId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PišeId entity = (PišeId) o;
        return Objects.equals(this.predmetId, entity.predmetId) &&
                Objects.equals(this.ispitId, entity.ispitId) &&
                Objects.equals(this.korisnikId, entity.korisnikId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(predmetId, ispitId, korisnikId);
    }

}