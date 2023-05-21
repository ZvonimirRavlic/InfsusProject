package com.example.app;

import com.example.dao.IspitRepository;
import com.example.dao.PredmetRepository;
import com.example.entity.Ispit;
import com.example.entity.IspitId;
import com.example.entity.Predmet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
public class IspitRepositoryTests {

    @Autowired
    private IspitRepository ispitRepository;
    @Autowired
    private PredmetRepository predmetRepository;

    @Test
    public void nemaProslog() {
        //Given

        //When
        final Optional<Ispit> prosli = ispitRepository.findFirstById_IspitIdLessThanAndId_PredmetIdOrderById_IspitIdDesc(1, 1);

        //Then
        assertTrue(prosli.isEmpty());
    }

    @Test
    public void imaProslog() {

        //Given
        final IspitId ispitId = new IspitId();
        ispitId.setIspitId(1);
        ispitId.setPredmetId(1);

        final Predmet predmet1 = new Predmet();
        predmet1.setId(1);
        predmet1.setGodina(1);
        predmet1.setNaziv("Engleski jezik");
        predmetRepository.save(predmet1);

        final Ispit ispit = new Ispit();
        ispit.setId(ispitId);
        ispit.setVrsta("Usmeni");
        ispit.setDatum(LocalDate.now());
        ispit.setNapomena("Napomena");
        ispit.setPredmetid(predmet1);
        ispitRepository.save(ispit);

        //When
        final Optional<Ispit> prosliOptional = ispitRepository.findFirstById_IspitIdLessThanAndId_PredmetIdOrderById_IspitIdDesc(2, 1);

        //Then
        assertTrue(prosliOptional.isPresent());
        final Ispit prosli = prosliOptional.get();
        assertEquals(ispit.getId(), prosli.getId());
        assertEquals(ispit.getVrsta(), prosli.getVrsta());
        assertEquals(ispit.getDatum(), prosli.getDatum());
        assertEquals(ispit.getNapomena(), prosli.getNapomena());
        assertEquals(ispit.getPredmetid().getId(), prosli.getPredmetid().getId());
    }


    @Test
    public void nemaSljedeceg() {

        //Given

        //When
        final Optional<Ispit> prosli = ispitRepository.findFirstById_IspitIdGreaterThanAndId_PredmetIdOrderById_IspitIdAsc(1, 1);

        //Then
        assertTrue(prosli.isEmpty());
    }

    @Test
    public void imaSljedeceg() {

        //Given
        final IspitId ispitId = new IspitId();
        ispitId.setIspitId(2);
        ispitId.setPredmetId(1);

        final Predmet predmet1 = new Predmet();
        predmet1.setId(1);
        predmet1.setGodina(1);
        predmet1.setNaziv("Engleski jezik");

        predmetRepository.save(predmet1);

        final Ispit ispit = new Ispit();
        ispit.setId(ispitId);
        ispit.setVrsta("Usmeni");
        ispit.setDatum(LocalDate.now());
        ispit.setNapomena("Napomena");
        ispit.setPredmetid(predmet1);
        ispitRepository.save(ispit);

        //When
        final Optional<Ispit> sljedeciOptional = ispitRepository.findFirstById_IspitIdGreaterThanAndId_PredmetIdOrderById_IspitIdAsc(1, 1);

        //Then
        assertTrue(sljedeciOptional.isPresent());
        final Ispit sljedeci = sljedeciOptional.get();
        assertEquals(ispit.getId(), sljedeci.getId());
        assertEquals(ispit.getVrsta(), sljedeci.getVrsta());
        assertEquals(ispit.getDatum(), sljedeci.getDatum());
        assertEquals(ispit.getNapomena(), sljedeci.getNapomena());
        assertEquals(ispit.getPredmetid().getId(), sljedeci.getPredmetid().getId());
    }

    @Test
    public void nemaPoId() {

        //Given
        final IspitId ispitId = new IspitId();
        ispitId.setIspitId(2);
        ispitId.setPredmetId(1);

        //When
        final Optional<Ispit> prosli = ispitRepository.findById(ispitId);


        //Then
        assertTrue(prosli.isEmpty());
    }

    @Test
    public void imaPoId() {

        //Given
        final IspitId ispitId = new IspitId();
        ispitId.setIspitId(2);
        ispitId.setPredmetId(1);

        final Predmet predmet1 = new Predmet();
        predmet1.setId(1);
        predmet1.setGodina(1);
        predmet1.setNaziv("Engleski jezik");

        predmetRepository.save(predmet1);

        final Ispit ispit = new Ispit();
        ispit.setId(ispitId);
        ispit.setVrsta("Usmeni");
        ispit.setDatum(LocalDate.now());
        ispit.setNapomena("Napomena");
        ispit.setPredmetid(predmet1);
        ispitRepository.save(ispit);

        //When
        final Optional<Ispit> poIdOptional = ispitRepository.findById(ispitId);

        //Then
        assertTrue(poIdOptional.isPresent());
        final Ispit poId = poIdOptional.get();
        assertEquals(ispit.getId(), poId.getId());
        assertEquals(ispit.getVrsta(), poId.getVrsta());
        assertEquals(ispit.getDatum(), poId.getDatum());
        assertEquals(ispit.getNapomena(), poId.getNapomena());
        assertEquals(ispit.getPredmetid().getId(), poId.getPredmetid().getId());
    }

    @Test
    public void seqId() {

        //Given

        //When
        final Integer id = ispitRepository.getNextId();

        //Then
        assertEquals(1, id);
    }

}
