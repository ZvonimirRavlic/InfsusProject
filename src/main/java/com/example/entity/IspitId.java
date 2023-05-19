package com.example.entity;

import jakarta.persistence.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class IspitId implements Serializable {
    private static final long serialVersionUID = 792967473418854067L;
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ispit_id_seq"
    )
    @SequenceGenerator(
            name = "ispit_id_seq",
            sequenceName = "ispit_id_seq",
            allocationSize=1
    )
    @Column(name = "ispitid", nullable = false)
    private Integer ispitId;

    @Column(name = "predmetid", nullable = false)
    private Integer predmetId;

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
        IspitId entity = (IspitId) o;
        return Objects.equals(this.predmetId, entity.predmetId) &&
                Objects.equals(this.ispitId, entity.ispitId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(predmetId, ispitId);
    }

}