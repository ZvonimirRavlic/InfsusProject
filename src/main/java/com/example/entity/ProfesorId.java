package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProfesorId implements Serializable {
    private static final long serialVersionUID = 7434702730104522084L;
    @Column(name = "korisnikid", nullable = false)
    private Integer korisnikId;

    @Column(name = "titula", nullable = false, length = 256)
    private String titula;

    public Integer getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(Integer korisnikId) {
        this.korisnikId = korisnikId;
    }

    public String getTitula() {
        return titula;
    }

    public void setTitula(String titula) {
        this.titula = titula;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ProfesorId entity = (ProfesorId) o;
        return Objects.equals(this.titula, entity.titula) &&
                Objects.equals(this.korisnikId, entity.korisnikId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titula, korisnikId);
    }

}