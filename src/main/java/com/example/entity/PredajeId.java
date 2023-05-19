package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PredajeId implements Serializable {
    private static final long serialVersionUID = 6054251547319158169L;
    @Column(name = "razredid", nullable = false)
    private Integer razredid;

    @Column(name = "predmetid", nullable = false)
    private Integer predmetid;

    public Integer getRazredid() {
        return razredid;
    }

    public void setRazredid(Integer razredid) {
        this.razredid = razredid;
    }

    public Integer getPredmetid() {
        return predmetid;
    }

    public void setPredmetid(Integer predmetid) {
        this.predmetid = predmetid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PredajeId entity = (PredajeId) o;
        return Objects.equals(this.razredid, entity.razredid) &&
                Objects.equals(this.predmetid, entity.predmetid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(razredid, predmetid);
    }

}