package com.example.entity;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Razred {
    @Id
    @Column(name = "razredid", nullable = false)
    private Integer id;

    @Column(name = "godina", nullable = false)
    private Integer godina;

    @Column(name = "odjeljenje", nullable = false, length = 1)
    private String odjeljenje;

    @OneToMany
    @JoinColumn(name = "razredid")
    private Set<Ucenik> ucenici = new LinkedHashSet<>();


    public Set<Ucenik> getUcenici() {
        return ucenici;
    }

    public void setUcenici(Set<Ucenik> ucenici) {
        this.ucenici = ucenici;
    }

    public String getOdjeljenje() {
        return odjeljenje;
    }

    public void setOdjeljenje(String odjeljenje) {
        this.odjeljenje = odjeljenje;
    }

    public Integer getGodina() {
        return godina;
    }

    public void setGodina(Integer godina) {
        this.godina = godina;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
