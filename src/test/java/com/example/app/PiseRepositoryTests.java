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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
public class PiseRepositoryTests {

    @Autowired
    private PiseRepository piseRepository;
    @Autowired
    private PredmetRepository predmetRepository;
    @Autowired
    private KorisnikRepository korisnikRepository;
    @Autowired
    private UcenikRepostory ucenikRepostory;
    @Autowired
    private IspitRepository ispitRepository;
    @Autowired
    private RazredRepository razredRepository;

    @Test
    public void postojiPoId() {

        //Given
        final Predmet predmet = new Predmet();
        predmet.setId(1);
        predmet.setGodina(1);
        predmet.setNaziv("Engleski jezik");
        predmetRepository.save(predmet);

        final Korisnik korisnik = new Korisnik();
        korisnik.setId(1);
        korisnik.setIme("Ime");
        korisnik.setUsername("Username");
        korisnik.setPrezime("Prezime");
        korisnik.setOib("12345678901");
        korisnik.setSifra("Sifra");
        korisnikRepository.save(korisnik);

        final Razred razred = new Razred();
        razred.setId(1);
        razred.setOdjeljenje("A");
        razred.setGodina(1);
        razredRepository.save(razred);

        final Ucenik ucenik = new Ucenik();
        ucenik.setId(1);
        ucenik.setRazredid(1);
        ucenikRepostory.save(ucenik);
        ucenik.setKorisnik(korisnik);
        ucenikRepostory.save(ucenik);

        final IspitId ispitId1 = new IspitId();
        ispitId1.setIspitId(1);
        ispitId1.setPredmetId(1);

        final Ispit ispit1 = new Ispit();
        ispit1.setId(ispitId1);
        ispit1.setVrsta("Usmeni");
        ispit1.setDatum(LocalDate.now());
        ispit1.setNapomena("Napomena");
        ispit1.setPredmetid(predmet);
        ispitRepository.save(ispit1);

        final PišeId piseId1 = new PišeId();
        piseId1.setKorisnikId(1);
        piseId1.setIspitId(1);
        piseId1.setPredmetId(1);

        final Piše pise1 = new Piše();
        pise1.setOcjena(5);
        pise1.setIspitId(1);
        pise1.setPredmetId(1);
        pise1.setNapomena("Napomena");
        pise1.setKorisnikId(ucenik);
        pise1.setId(piseId1);
        piseRepository.save(pise1);

        //When
        final boolean postoji = piseRepository.existsById(piseId1);

        //Then
        assertTrue(postoji);
    }

    @Test
    public void nePostojiPoId() {

        //Given
        final Predmet predmet = new Predmet();
        predmet.setId(1);
        predmet.setGodina(1);
        predmet.setNaziv("Engleski jezik");
        predmetRepository.save(predmet);

        final Korisnik korisnik = new Korisnik();
        korisnik.setId(1);
        korisnik.setIme("Ime");
        korisnik.setUsername("Username");
        korisnik.setPrezime("Prezime");
        korisnik.setOib("12345678901");
        korisnik.setSifra("Sifra");
        korisnikRepository.save(korisnik);

        final Razred razred = new Razred();
        razred.setId(1);
        razred.setOdjeljenje("A");
        razred.setGodina(1);
        razredRepository.save(razred);

        final Ucenik ucenik = new Ucenik();
        ucenik.setId(1);
        ucenik.setRazredid(1);
        ucenikRepostory.save(ucenik);
        ucenik.setKorisnik(korisnik);
        ucenikRepostory.save(ucenik);

        final IspitId ispitId1 = new IspitId();
        ispitId1.setIspitId(1);
        ispitId1.setPredmetId(1);

        final Ispit ispit1 = new Ispit();
        ispit1.setId(ispitId1);
        ispit1.setVrsta("Usmeni");
        ispit1.setDatum(LocalDate.now());
        ispit1.setNapomena("Napomena");
        ispit1.setPredmetid(predmet);
        ispitRepository.save(ispit1);

        final PišeId piseId1 = new PišeId();
        piseId1.setKorisnikId(1);
        piseId1.setIspitId(1);
        piseId1.setPredmetId(1);

        //When
        final boolean postoji = piseRepository.existsById(piseId1);

        //Then
        assertFalse(postoji);
    }


    @Test
    public void postojiFindByIspitIdAndPredmetId() {

        //Given
        final Predmet predmet = new Predmet();
        predmet.setId(1);
        predmet.setGodina(1);
        predmet.setNaziv("Engleski jezik");
        predmetRepository.save(predmet);

        final Korisnik korisnik = new Korisnik();
        korisnik.setId(1);
        korisnik.setIme("Ime");
        korisnik.setUsername("Username");
        korisnik.setPrezime("Prezime");
        korisnik.setOib("12345678901");
        korisnik.setSifra("Sifra");
        korisnikRepository.save(korisnik);

        final Razred razred = new Razred();
        razred.setId(1);
        razred.setOdjeljenje("A");
        razred.setGodina(1);
        razredRepository.save(razred);

        final Ucenik ucenik = new Ucenik();
        ucenik.setId(1);
        ucenik.setRazredid(1);
        ucenikRepostory.save(ucenik);
        ucenik.setKorisnik(korisnik);
        ucenikRepostory.save(ucenik);

        final IspitId ispitId1 = new IspitId();
        ispitId1.setIspitId(1);
        ispitId1.setPredmetId(1);

        final Ispit ispit1 = new Ispit();
        ispit1.setId(ispitId1);
        ispit1.setVrsta("Usmeni");
        ispit1.setDatum(LocalDate.now());
        ispit1.setNapomena("Napomena");
        ispit1.setPredmetid(predmet);
        ispitRepository.save(ispit1);

        final PišeId piseId1 = new PišeId();
        piseId1.setKorisnikId(1);
        piseId1.setIspitId(1);
        piseId1.setPredmetId(1);

        final Piše pise1 = new Piše();
        pise1.setId(piseId1);
        pise1.setOcjena(5);
        pise1.setIspitId(1);
        pise1.setPredmetId(1);
        pise1.setNapomena("Napomena");
        pise1.setKorisnikId(ucenik);
        piseRepository.save(pise1);

        //When
        final List<Piše> piseList = piseRepository.findByIspitIdAndPredmetId(1,1);

        //Then
        assertEquals(1, piseList.size());
        final Piše pise2 = piseList.get(0);
        assertEquals(pise1.getId(), pise2.getId());
        assertEquals(pise1.getOcjena(), pise2.getOcjena());
        assertEquals(pise1.getIspitId(), pise2.getIspitId());
        assertEquals(pise1.getPredmetId(), pise2.getPredmetId());
        assertEquals(pise1.getNapomena(), pise2.getNapomena());
        assertEquals(pise1.getKorisnikId().getId(), pise2.getKorisnikId().getId());
    }

    @Test
    public void nePostojiFindByIspitIdAndPredmetId() {

        //Given
        final Predmet predmet = new Predmet();
        predmet.setId(1);
        predmet.setGodina(1);
        predmet.setNaziv("Engleski jezik");
        predmetRepository.save(predmet);

        final Korisnik korisnik = new Korisnik();
        korisnik.setId(1);
        korisnik.setIme("Ime");
        korisnik.setUsername("Username");
        korisnik.setPrezime("Prezime");
        korisnik.setOib("12345678901");
        korisnik.setSifra("Sifra");
        korisnikRepository.save(korisnik);

        final Razred razred = new Razred();
        razred.setId(1);
        razred.setOdjeljenje("A");
        razred.setGodina(1);
        razredRepository.save(razred);

        final Ucenik ucenik = new Ucenik();
        ucenik.setId(1);
        ucenik.setRazredid(1);
        ucenikRepostory.save(ucenik);
        ucenik.setKorisnik(korisnik);
        ucenikRepostory.save(ucenik);

        final IspitId ispitId1 = new IspitId();
        ispitId1.setIspitId(1);
        ispitId1.setPredmetId(1);

        final Ispit ispit1 = new Ispit();
        ispit1.setId(ispitId1);
        ispit1.setVrsta("Usmeni");
        ispit1.setDatum(LocalDate.now());
        ispit1.setNapomena("Napomena");
        ispit1.setPredmetid(predmet);
        ispitRepository.save(ispit1);

        final PišeId piseId1 = new PišeId();
        piseId1.setKorisnikId(1);
        piseId1.setIspitId(1);
        piseId1.setPredmetId(1);

        //When
        final List<Piše> piseList = piseRepository.findByIspitIdAndPredmetId(1,1);

        //Then
        assertTrue(piseList.isEmpty());
    }

    @Test
    public void ocjeneNaDatumZaUcenika(){

        //Given
        final Predmet predmet = new Predmet();
        predmet.setId(1);
        predmet.setGodina(1);
        predmet.setNaziv("Engleski jezik");
        predmetRepository.save(predmet);

        final Korisnik korisnik = new Korisnik();
        korisnik.setId(1);
        korisnik.setIme("Ime");
        korisnik.setUsername("Username");
        korisnik.setPrezime("Prezime");
        korisnik.setOib("12345678901");
        korisnik.setSifra("Sifra");
        korisnikRepository.save(korisnik);

        final Razred razred = new Razred();
        razred.setId(1);
        razred.setOdjeljenje("A");
        razred.setGodina(1);
        razredRepository.save(razred);

        final Ucenik ucenik = new Ucenik();
        ucenik.setId(1);
        ucenik.setRazredid(1);
        ucenikRepostory.save(ucenik);
        ucenik.setKorisnik(korisnik);
        ucenikRepostory.save(ucenik);

        final IspitId ispitId1 = new IspitId();
        ispitId1.setIspitId(1);
        ispitId1.setPredmetId(1);

        final Ispit ispit1 = new Ispit();
        ispit1.setId(ispitId1);
        ispit1.setVrsta("Usmeni");
        ispit1.setDatum(LocalDate.now());
        ispit1.setNapomena("Napomena");
        ispit1.setPredmetid(predmet);
        ispitRepository.save(ispit1);

        final PišeId piseId1 = new PišeId();
        piseId1.setKorisnikId(1);
        piseId1.setIspitId(1);
        piseId1.setPredmetId(1);

        final Piše pise1 = new Piše();
        pise1.setId(piseId1);
        pise1.setOcjena(5);
        pise1.setIspitId(1);
        pise1.setPredmetId(1);
        pise1.setNapomena("Napomena");
        pise1.setKorisnikId(ucenik);
        piseRepository.save(pise1);

        //When
        final List<Piše> piseList = piseRepository.findOcjeneNaDatumZaUcenika(LocalDate.now(),1);

        //Then
        assertEquals(1, piseList.size());
        final Piše pise2 = piseList.get(0);
        assertEquals(pise1.getId(), pise2.getId());
        assertEquals(pise1.getOcjena(), pise2.getOcjena());
        assertEquals(pise1.getIspitId(), pise2.getIspitId());
        assertEquals(pise1.getPredmetId(), pise2.getPredmetId());
        assertEquals(pise1.getNapomena(), pise2.getNapomena());
        assertEquals(pise1.getKorisnikId().getId(), pise2.getKorisnikId().getId());

    }

    @Test
    public void nemaOcjenaNaDatumZaUcenika(){

        //Given
        final Predmet predmet = new Predmet();
        predmet.setId(1);
        predmet.setGodina(1);
        predmet.setNaziv("Engleski jezik");
        predmetRepository.save(predmet);

        final Korisnik korisnik = new Korisnik();
        korisnik.setId(1);
        korisnik.setIme("Ime");
        korisnik.setUsername("Username");
        korisnik.setPrezime("Prezime");
        korisnik.setOib("12345678901");
        korisnik.setSifra("Sifra");
        korisnikRepository.save(korisnik);

        final Razred razred = new Razred();
        razred.setId(1);
        razred.setOdjeljenje("A");
        razred.setGodina(1);
        razredRepository.save(razred);

        final Ucenik ucenik = new Ucenik();
        ucenik.setId(1);
        ucenik.setRazredid(1);
        ucenikRepostory.save(ucenik);
        ucenik.setKorisnik(korisnik);
        ucenikRepostory.save(ucenik);

        final IspitId ispitId1 = new IspitId();
        ispitId1.setIspitId(1);
        ispitId1.setPredmetId(1);

        final Ispit ispit1 = new Ispit();
        ispit1.setId(ispitId1);
        ispit1.setVrsta("Usmeni");
        ispit1.setDatum(LocalDate.now());
        ispit1.setNapomena("Napomena");
        ispit1.setPredmetid(predmet);
        ispitRepository.save(ispit1);

        //When
        final List<Piše> piseList = piseRepository.findOcjeneNaDatumZaUcenika(LocalDate.now(),1);

        //Then
        assertTrue( piseList.isEmpty());

    }
}
