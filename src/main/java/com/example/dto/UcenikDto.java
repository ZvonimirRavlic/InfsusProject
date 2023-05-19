package com.example.dto;

import java.util.Objects;

public class UcenikDto {
    public int korisnikId;
    public String imePrezime;

    public int getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(int korisnikId) {
        this.korisnikId = korisnikId;
    }

    public String getImePrezime() {
        return imePrezime;
    }

    public void setImePrezime(String imePrezime) {
        this.imePrezime = imePrezime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UcenikDto ucenikDto = (UcenikDto) o;
        return korisnikId == ucenikDto.korisnikId && Objects.equals(imePrezime, ucenikDto.imePrezime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(korisnikId, imePrezime);
    }
}
