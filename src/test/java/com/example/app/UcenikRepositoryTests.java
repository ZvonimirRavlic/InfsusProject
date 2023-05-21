package com.example.app;

import com.example.dao.*;
import com.example.entity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
public class UcenikRepositoryTests {

    @Autowired
    private UcenikRepostory ucenikRepostory;
    @Autowired
    private KorisnikRepository korisnikRepository;
    @Autowired
    private RazredRepository razredRepository;
    @Autowired
    private PredmetRepository predmetRepository;
    @Autowired
    private PredajeRepository predajeRepository;


    @Test
    public void nemaUcenikaNaPredmetu() {

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

        final Korisnik prof = new Korisnik();
        prof.setId(2);
        prof.setIme("Ime");
        prof.setUsername("Username");
        prof.setPrezime("Prezime");
        prof.setOib("12345678901");
        prof.setSifra("Sifra");
        korisnikRepository.save(prof);

        final PredajeId predajeId = new PredajeId();
        predajeId.setRazredid(1);
        predajeId.setPredmetid(1);

        final Predaje predaje = new Predaje();
        predaje.setId(predajeId);
        predaje.setPredmetid(predmet1);
        predaje.setRazredid(razred);
        predaje.setKorisnikId(prof);
        predajeRepository.save(predaje);

        //When
        List<Ucenik> ucenici = ucenikRepostory.findUcenikeNaPredmetu(1);

        //Then
        assertEquals(0, ucenici.size());


    }

    @Test
    public void imaUcenikaNaPredmetu() {

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

        final Korisnik prof = new Korisnik();
        prof.setId(2);
        prof.setIme("Ime");
        prof.setUsername("Username");
        prof.setPrezime("Prezime");
        prof.setOib("12345678901");
        prof.setSifra("Sifra");
        korisnikRepository.save(prof);

        final PredajeId predajeId = new PredajeId();
        predajeId.setRazredid(1);
        predajeId.setPredmetid(1);

        final Predaje predaje = new Predaje();
        predaje.setId(predajeId);
        predaje.setPredmetid(predmet1);
        predaje.setRazredid(razred);
        predaje.setKorisnikId(prof);
        predajeRepository.save(predaje);

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
        List<Ucenik> ucenici = ucenikRepostory.findUcenikeNaPredmetu(1);

        //Then
        assertEquals(1, ucenici.size());


    }

    @Test
    public void nemaPoId() {

        //Given

        //When
        Optional<Ucenik> ucenikOptional = ucenikRepostory.findById(1);

        //Then
        assertTrue(ucenikOptional.isEmpty());

    }

    @Test
    public void imaPoId() {

        //Given
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
        Optional<Ucenik> ucenikOptional = ucenikRepostory.findById(1);

        //Then
        assertTrue(ucenikOptional.isPresent());
        final Ucenik ucenikPoId = ucenikOptional.get();
        assertEquals(ucenik.getId(), ucenikPoId.getId());
        assertEquals(ucenik.getRazredid(), ucenikPoId.getRazredid());
        assertEquals(ucenik.getKorisnik().getId(), ucenikPoId.getKorisnik().getId());

    }
}
