package com.example.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Predmet {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "predmet_id_seq"
    )
    @SequenceGenerator(
            name = "predmet_id_seq",
            sequenceName = "predmet_id_seq",
            allocationSize=1
    )
    @Column(name = "predmetid", nullable = false)
    private Integer id;
    @Column(name = "naziv", nullable = false, length = 256)
    private String naziv;
    @Column(name = "godina", nullable = false)
    private Integer godina;
    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
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
