package com.example.app;

import com.example.dao.*;
import com.example.entity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
public class IzostanakRepositoryTests {

    @Autowired
    private IzostanakRepository izostanakRepository;

    @Autowired
    private PredmetRepository predmetRepository;

    @Autowired
    private RazredRepository razredRepository;
    @Autowired
    private KorisnikRepository korisnikRepository;
    @Autowired
    private UcenikRepostory ucenikRepostory;

    @Test
    public void postoji() {

        //Given
        final Predmet predmet1 = new Predmet();
        predmet1.setId(1);
        predmet1.setGodina(1);
        predmet1.setNaziv("Engleski jezik");
        predmetRepository.save(predmet1);

        final Razred razred = new Razred();
        razred.setId(1);
        razred.setOdjeljenje("A");
        razred.setGodina(1);
        razredRepository.save(razred);


        final Korisnik korisnik = new Korisnik();
        korisnik.setId(1);
        korisnik.setIme("Ime");
        korisnik.setUsername("Username");
        korisnik.setPrezime("Prezime");
        korisnik.setOib("12345678901");
        korisnik.setSifra("Sifra");
        korisnikRepository.save(korisnik);

        final Ucenik ucenik = new Ucenik();
        ucenik.setId(1);
        ucenik.setRazredid(1);
        ucenikRepostory.save(ucenik);
        ucenik.setKorisnik(korisnik);
        ucenikRepostory.save(ucenik);

        final Izostanak izostanak = new Izostanak();
        izostanak.setId(1);
        izostanak.setVrsta("Neopravdano");
        izostanak.setPredmetid(predmet1);
        izostanak.setKorisnikId(ucenik);
        izostanak.setDatum(LocalDate.now());
        izostanak.setNapomena("Napomena");
        izostanak.setNapomenaroditelja("NapomenaRoditelja");
        izostanakRepository.save(izostanak);

        //When
        final boolean posotji = izostanakRepository.existsByDatumAndKorisnikId_IdAndPredmetid_Id(LocalDate.now(),1,1);

        //Then
        assertTrue(posotji);
    }

    @Test
    public void nePostoji() {

        //Given
        final Predmet predmet1 = new Predmet();
        predmet1.setId(1);
        predmet1.setGodina(1);
        predmet1.setNaziv("Engleski jezik");
        predmetRepository.save(predmet1);

        final Razred razred = new Razred();
        razred.setId(1);
        razred.setOdjeljenje("A");
        razred.setGodina(1);
        razredRepository.save(razred);


        final Korisnik korisnik = new Korisnik();
        korisnik.setId(1);
        korisnik.setIme("Ime");
        korisnik.setUsername("Username");
        korisnik.setPrezime("Prezime");
        korisnik.setOib("12345678901");
        korisnik.setSifra("Sifra");
        korisnikRepository.save(korisnik);

        final Ucenik ucenik = new Ucenik();
        ucenik.setId(1);
        ucenik.setRazredid(1);
        ucenikRepostory.save(ucenik);
        ucenik.setKorisnik(korisnik);
        ucenikRepostory.save(ucenik);

        //When
        final boolean postoji = izostanakRepository.existsByDatumAndKorisnikId_IdAndPredmetid_Id(LocalDate.now(),1,1);

        //Then
        assertFalse(postoji);
    }
}
