package com.example.app;

import com.example.dao.*;
import com.example.dto.*;
import com.example.entity.*;
import com.example.service.IspitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
class IspitServiceTests {

    @Autowired
    private IspitService ispitService;
    @MockBean
    private IspitRepository ispitRepository;
    @MockBean
    private PredmetRepository predmetRepository;
    @MockBean
    private UcenikRepostory ucenikRepostory;
    @MockBean
    private IzostanakRepository izostanakRepository;
    @MockBean
    private PiseRepository piseRepository;

    @BeforeEach
    void resetMocks() {
        Mockito.reset(ispitRepository);
        Mockito.reset(predmetRepository);
        Mockito.reset(ucenikRepostory);
        Mockito.reset(piseRepository);
        Mockito.reset(izostanakRepository);
    }

    @Test
    void getIspit__nepostojeciIspit() {

        //Given

        when(ispitRepository.findById(any(IspitId.class))).thenReturn(empty());

        //When
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ispitService.getIspit(1, 1);
        });

        //Then
        assertEquals("Ne postoji ispit sa tim id-em za ovaj predmet!", exception.getMessage());
    }


    @Test
    void getIspit() {

        //Given
        final IspitId ispitId = new IspitId();
        ispitId.setIspitId(1);
        ispitId.setPredmetId(1);

        final Predmet predmet1 = new Predmet();
        predmet1.setId(1);
        predmet1.setGodina(1);
        predmet1.setNaziv("Engleski jezik");

        final Predmet predmet2 = new Predmet();
        predmet2.setId(2);
        predmet2.setGodina(2);
        predmet2.setNaziv("Hrvatski jezik");

        final Ispit ispit = new Ispit();
        ispit.setId(ispitId);
        ispit.setVrsta("Usmeni");
        ispit.setDatum(LocalDate.now());
        ispit.setNapomena("Napomena");
        ispit.setPredmetid(predmet1);

        when(predmetRepository.findAll()).thenReturn(List.of(predmet1, predmet2));
        when(ispitRepository.findById(any(IspitId.class))).thenReturn(Optional.of(ispit));
        when(piseRepository.findByIspitIdAndPredmetId(any(), any())).thenReturn(List.of());
        when(ucenikRepostory.findUcenikeNaPredmetu(any())).thenReturn(List.of());

        //When
        final IspitDto ispitDto = ispitService.getIspit(1, 1);

        //Then
        assertNotNull(ispitDto);
        assertEquals(ispit.getId().getIspitId(), ispitDto.getIspitId().getIspitId());
        assertEquals(ispit.getId().getPredmetId(), ispitDto.getIspitId().getPredmetId());
        assertEquals(ispit.getPredmetid().getId(), ispitDto.getPredmeti().get(0).getPredmetId());
        assertEquals(2, ispitDto.getPredmeti().size());
        assertEquals(ispit.getDatum(), ispitDto.getDatum());
        assertEquals(ispit.getNapomena(), ispitDto.getNapomena());
        assertEquals(ispit.getVrsta(), ispitDto.getVrsta());
        assertTrue(ispitDto.getOcjene().isEmpty());
        assertTrue(ispitDto.getUcenici().isEmpty());
    }

    @Test
    void getIspiti() {

        //Given
        final IspitId ispitId1 = new IspitId();
        ispitId1.setIspitId(1);
        ispitId1.setPredmetId(1);

        final IspitId ispitId2 = new IspitId();
        ispitId2.setIspitId(2);
        ispitId2.setPredmetId(1);

        final Predmet predmet = new Predmet();
        predmet.setId(1);
        predmet.setGodina(1);
        predmet.setNaziv("Engleski jezik");

        final Ispit ispit1 = new Ispit();
        ispit1.setId(ispitId1);
        ispit1.setVrsta("Usmeni");
        ispit1.setDatum(LocalDate.now());
        ispit1.setNapomena("Napomena");
        ispit1.setPredmetid(predmet);


        final Ispit ispit2 = new Ispit();
        ispit2.setId(ispitId2);
        ispit2.setVrsta("Usmeni");
        ispit2.setDatum(LocalDate.now());
        ispit2.setNapomena("Napomena");
        ispit2.setPredmetid(predmet);

        when(ispitRepository.findAll()).thenReturn(List.of(ispit1, ispit2));
        when(predmetRepository.findAll()).thenReturn(List.of(predmet));
        when(piseRepository.findByIspitIdAndPredmetId(any(), any())).thenReturn(List.of());
        when(ucenikRepostory.findUcenikeNaPredmetu(any())).thenReturn(List.of());


        //When
        final List<IspitiResp> ispiti = ispitService.getIspiti();

        //Then
        assertEquals(2, ispiti.size());

    }

    @Test
    void getIspitSljedeci_NePostoji() {

        //Given

        when(ispitRepository.findFirstById_IspitIdGreaterThanAndId_PredmetIdOrderById_IspitIdAsc(any(), any())).thenReturn(empty());

        //When
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ispitService.getIspitSljedeci(1, 1);
        });

        //Then
        assertEquals("Ne postoji sljedeci ispit!", exception.getMessage());
    }

    @Test
    void getIspitSljedeci() {

        //Given
        final IspitId ispitId2 = new IspitId();
        ispitId2.setIspitId(2);
        ispitId2.setPredmetId(1);

        final Predmet predmet = new Predmet();
        predmet.setId(1);
        predmet.setGodina(1);
        predmet.setNaziv("Engleski jezik");


        final Ispit ispit2 = new Ispit();
        ispit2.setId(ispitId2);
        ispit2.setVrsta("Usmeni");
        ispit2.setDatum(LocalDate.now());
        ispit2.setNapomena("Napomena");
        ispit2.setPredmetid(predmet);


        when(predmetRepository.findAll()).thenReturn(List.of(predmet));
        when(ispitRepository.findFirstById_IspitIdGreaterThanAndId_PredmetIdOrderById_IspitIdAsc(any(), any())).thenReturn(Optional.of(ispit2));
        when(piseRepository.findByIspitIdAndPredmetId(any(), any())).thenReturn(List.of());
        when(ucenikRepostory.findUcenikeNaPredmetu(any())).thenReturn(List.of());

        //When
        final IspitDto ispitDto = ispitService.getIspitSljedeci(1, 1);


        //Then
        assertNotNull(ispitDto);
        assertEquals(ispit2.getId().getIspitId(), ispitDto.getIspitId().getIspitId());
        assertEquals(ispit2.getId().getPredmetId(), ispitDto.getIspitId().getPredmetId());
        assertEquals(ispit2.getPredmetid().getId(), ispitDto.getPredmeti().get(0).getPredmetId());
        assertEquals(1, ispitDto.getPredmeti().size());
        assertEquals(ispit2.getDatum(), ispitDto.getDatum());
        assertEquals(ispit2.getNapomena(), ispitDto.getNapomena());
        assertEquals(ispit2.getVrsta(), ispitDto.getVrsta());
        assertTrue(ispitDto.getOcjene().isEmpty());
        assertTrue(ispitDto.getUcenici().isEmpty());
    }


    @Test
    void getIspitProsli_NePostoji() {

        //Given
        when(ispitRepository.findFirstById_IspitIdLessThanAndId_PredmetIdOrderById_IspitIdDesc(any(), any())).thenReturn(empty());

        //When
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ispitService.getIspitProsli(1, 1);
        });

        //Then
        assertEquals("Ne postoji prosli ispit!", exception.getMessage());
    }

    @Test
    void getIspitProsli() {

        //Given
        final IspitId ispitId1 = new IspitId();
        ispitId1.setIspitId(1);
        ispitId1.setPredmetId(1);

        final Predmet predmet = new Predmet();
        predmet.setId(1);
        predmet.setGodina(1);
        predmet.setNaziv("Engleski jezik");

        final Ispit ispit1 = new Ispit();
        ispit1.setId(ispitId1);
        ispit1.setVrsta("Usmeni");
        ispit1.setDatum(LocalDate.now());
        ispit1.setNapomena("Napomena");
        ispit1.setPredmetid(predmet);

        when(predmetRepository.findAll()).thenReturn(List.of(predmet));
        when(ispitRepository.findFirstById_IspitIdLessThanAndId_PredmetIdOrderById_IspitIdDesc(any(), any())).thenReturn(Optional.of(ispit1));
        when(piseRepository.findByIspitIdAndPredmetId(any(), any())).thenReturn(List.of());
        when(ucenikRepostory.findUcenikeNaPredmetu(any())).thenReturn(List.of());

        //When
        final IspitDto ispitDto = ispitService.getIspitProsli(1, 2);


        //Then
        assertNotNull(ispitDto);
        assertEquals(ispit1.getId().getIspitId(), ispitDto.getIspitId().getIspitId());
        assertEquals(ispit1.getId().getPredmetId(), ispitDto.getIspitId().getPredmetId());
        assertEquals(ispit1.getPredmetid().getId(), ispitDto.getPredmeti().get(0).getPredmetId());
        assertEquals(1, ispitDto.getPredmeti().size());
        assertEquals(ispit1.getDatum(), ispitDto.getDatum());
        assertEquals(ispit1.getNapomena(), ispitDto.getNapomena());
        assertEquals(ispit1.getVrsta(), ispitDto.getVrsta());
        assertTrue(ispitDto.getOcjene().isEmpty());
        assertTrue(ispitDto.getUcenici().isEmpty());
    }

    @Test
    void delete__izbrisaniSviIspiti() {

        //Given
        when(ispitRepository.findFirstById_IspitIdLessThanAndId_PredmetIdOrderById_IspitIdDesc(any(), any())).thenReturn(empty());
        when(ispitRepository.findFirstById_IspitIdGreaterThanAndId_PredmetIdOrderById_IspitIdAsc(any(), any())).thenReturn(empty());
        doNothing().when(ispitRepository).deleteById(any(IspitId.class));
        //When
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ispitService.deleteIspit(1, 1);
        });

        //Then
        assertEquals("Izbrisali ste sve ispite!", exception.getMessage());
    }


    @Test
    void deleteIspit__imaProsli() {

        //Given
        final IspitId ispitId1 = new IspitId();
        ispitId1.setIspitId(1);
        ispitId1.setPredmetId(1);

        final Predmet predmet = new Predmet();
        predmet.setId(1);
        predmet.setGodina(1);
        predmet.setNaziv("Engleski jezik");

        final Ispit ispit1 = new Ispit();
        ispit1.setId(ispitId1);
        ispit1.setVrsta("Usmeni");
        ispit1.setDatum(LocalDate.now());
        ispit1.setNapomena("Napomena");
        ispit1.setPredmetid(predmet);


        when(ispitRepository.findFirstById_IspitIdLessThanAndId_PredmetIdOrderById_IspitIdDesc(any(), any())).thenReturn(of(ispit1));
        when(ispitRepository.findFirstById_IspitIdGreaterThanAndId_PredmetIdOrderById_IspitIdAsc(any(), any())).thenReturn(empty());
        doNothing().when(ispitRepository).deleteById(any(IspitId.class));
        when(predmetRepository.findAll()).thenReturn(List.of(predmet));
        when(piseRepository.findByIspitIdAndPredmetId(any(), any())).thenReturn(List.of());
        when(ucenikRepostory.findUcenikeNaPredmetu(any())).thenReturn(List.of());

        //When
        final IspitDto ispitDto = ispitService.deleteIspit(1, 2);

        //Then
        assertNotNull(ispitDto);
        assertEquals(ispit1.getId().getIspitId(), ispitDto.getIspitId().getIspitId());
        assertEquals(ispit1.getId().getPredmetId(), ispitDto.getIspitId().getPredmetId());
        assertEquals(ispit1.getPredmetid().getId(), ispitDto.getPredmeti().get(0).getPredmetId());
        assertEquals(1, ispitDto.getPredmeti().size());
        assertEquals(ispit1.getDatum(), ispitDto.getDatum());
        assertEquals(ispit1.getNapomena(), ispitDto.getNapomena());
        assertEquals(ispit1.getVrsta(), ispitDto.getVrsta());
        assertTrue(ispitDto.getOcjene().isEmpty());
        assertTrue(ispitDto.getUcenici().isEmpty());

    }


    @Test
    void deleteIspit__nemaProsli() {

        //Given
        final IspitId ispitId2 = new IspitId();
        ispitId2.setIspitId(2);
        ispitId2.setPredmetId(1);

        final Predmet predmet = new Predmet();
        predmet.setId(1);
        predmet.setGodina(1);
        predmet.setNaziv("Engleski jezik");


        final Ispit ispit2 = new Ispit();
        ispit2.setId(ispitId2);
        ispit2.setVrsta("Usmeni");
        ispit2.setDatum(LocalDate.now());
        ispit2.setNapomena("Napomena");
        ispit2.setPredmetid(predmet);


        when(ispitRepository.findFirstById_IspitIdLessThanAndId_PredmetIdOrderById_IspitIdDesc(any(), any())).thenReturn(empty());
        when(ispitRepository.findFirstById_IspitIdGreaterThanAndId_PredmetIdOrderById_IspitIdAsc(any(), any())).thenReturn(of(ispit2));
        doNothing().when(ispitRepository).deleteById(any(IspitId.class));
        when(predmetRepository.findAll()).thenReturn(List.of(predmet));
        when(piseRepository.findByIspitIdAndPredmetId(any(), any())).thenReturn(List.of());
        when(ucenikRepostory.findUcenikeNaPredmetu(any())).thenReturn(List.of());

        //When
        final IspitDto ispitDto = ispitService.deleteIspit(1, 1);

        //Then
        assertNotNull(ispitDto);
        assertEquals(ispit2.getId().getIspitId(), ispitDto.getIspitId().getIspitId());
        assertEquals(ispit2.getId().getPredmetId(), ispitDto.getIspitId().getPredmetId());
        assertEquals(ispit2.getPredmetid().getId(), ispitDto.getPredmeti().get(0).getPredmetId());
        assertEquals(1, ispitDto.getPredmeti().size());
        assertEquals(ispit2.getDatum(), ispitDto.getDatum());
        assertEquals(ispit2.getNapomena(), ispitDto.getNapomena());
        assertEquals(ispit2.getVrsta(), ispitDto.getVrsta());
        assertTrue(ispitDto.getOcjene().isEmpty());
        assertTrue(ispitDto.getUcenici().isEmpty());

    }


    @Test
    void deleteIspit__nnepostojeciIspit() {

        //Given
        final IspitId ispitId1 = new IspitId();
        ispitId1.setIspitId(1);
        ispitId1.setPredmetId(1);

        final Predmet predmet = new Predmet();
        predmet.setId(1);
        predmet.setGodina(1);
        predmet.setNaziv("Engleski jezik");

        final Ispit ispit1 = new Ispit();
        ispit1.setId(ispitId1);
        ispit1.setVrsta("Usmeni");
        ispit1.setDatum(LocalDate.now());
        ispit1.setNapomena("Napomena");
        ispit1.setPredmetid(predmet);


        when(ispitRepository.findFirstById_IspitIdLessThanAndId_PredmetIdOrderById_IspitIdDesc(any(), any())).thenReturn(of(ispit1));
        when(ispitRepository.findFirstById_IspitIdGreaterThanAndId_PredmetIdOrderById_IspitIdAsc(any(), any())).thenReturn(empty());
        doNothing().when(ispitRepository).deleteById(any(IspitId.class));
        when(predmetRepository.findAll()).thenReturn(List.of(predmet));
        when(piseRepository.findByIspitIdAndPredmetId(any(), any())).thenReturn(List.of());
        when(ucenikRepostory.findUcenikeNaPredmetu(any())).thenReturn(List.of());


        //When
        final IspitDto ispitDto = ispitService.deleteIspit(1, 2);

        //Then
        assertNotNull(ispitDto);
        assertEquals(ispit1.getId().getIspitId(), ispitDto.getIspitId().getIspitId());
        assertEquals(ispit1.getId().getPredmetId(), ispitDto.getIspitId().getPredmetId());
        assertEquals(ispit1.getPredmetid().getId(), ispitDto.getPredmeti().get(0).getPredmetId());
        assertEquals(1, ispitDto.getPredmeti().size());
        assertEquals(ispit1.getDatum(), ispitDto.getDatum());
        assertEquals(ispit1.getNapomena(), ispitDto.getNapomena());
        assertEquals(ispit1.getVrsta(), ispitDto.getVrsta());
        assertTrue(ispitDto.getOcjene().isEmpty());
        assertTrue(ispitDto.getUcenici().isEmpty());

    }

    @Test
    void createIspit__nepopunjenaPolja() {
        //Given

        final IspitCreate ispitCreate = new IspitCreate();

        //When
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ispitService.createIspit(ispitCreate);
        });

        //Then
        assertEquals("Nisu popunjena sva polja za kreiranje ispita!", exception.getMessage());
    }

    @Test
    void createIspit__nemaPredmeta() {

        //Given

        final IspitCreate ispitCreate = new IspitCreate();
        ispitCreate.setPredmetId(1);
        ispitCreate.setDatum(LocalDate.now());
        ispitCreate.setNapomena("Napomena");
        ispitCreate.setVrsta("Usmeni");

        when(ispitRepository.getNextId()).thenReturn(1);
        when(predmetRepository.findById(any())).thenReturn(empty());

        //When
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ispitService.createIspit(ispitCreate);
        });

        //Then
        assertEquals("Ne postoji predmet za koji zelite stvoriti ispit!", exception.getMessage());
    }

    @Test
    void createIspit() {

        //Given
        final Predmet predmet = new Predmet();
        predmet.setId(1);
        predmet.setGodina(1);
        predmet.setNaziv("Engleski jezik");

        final IspitCreate ispitCreate = new IspitCreate();
        ispitCreate.setPredmetId(1);
        ispitCreate.setDatum(LocalDate.now());
        ispitCreate.setNapomena("Napomena");
        ispitCreate.setVrsta("Usmeni");

        when(ispitRepository.getNextId()).thenReturn(1);
        when(predmetRepository.findById(any())).thenReturn(of(predmet));
        when(predmetRepository.findAll()).thenReturn(List.of(predmet));
        when(piseRepository.findByIspitIdAndPredmetId(any(), any())).thenReturn(List.of());
        when(ucenikRepostory.findUcenikeNaPredmetu(any())).thenReturn(List.of());

        //When
        final IspitDto ispitDto = ispitService.createIspit(ispitCreate);


        //Then
        assertNotNull(ispitDto);
        assertEquals(1, ispitDto.getIspitId().getIspitId());
        assertEquals(ispitCreate.getPredmetId(), ispitDto.getIspitId().getPredmetId());
        assertEquals(ispitCreate.getPredmetId(), ispitDto.getPredmeti().get(0).getPredmetId());
        assertEquals(1, ispitDto.getPredmeti().size());
        assertEquals(ispitCreate.getDatum(), ispitDto.getDatum());
        assertEquals(ispitCreate.getNapomena(), ispitDto.getNapomena());
        assertEquals(ispitCreate.getVrsta(), ispitDto.getVrsta());
        assertTrue(ispitDto.getOcjene().isEmpty());
        assertTrue(ispitDto.getUcenici().isEmpty());
    }


    @Test
    void updateIspit__nepopunjenaPolja() {

        //Given
        final IspitUpdate ispitUpdate = new IspitUpdate();

        //When
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ispitService.updateIspit(1, 1, ispitUpdate);
        });

        //Then
        assertEquals("Nisu popunjena sva polja za azuriranje ispita!", exception.getMessage());
    }

    @Test
    void updateIspit__nePostojiIspit() {

        //Given
        final IspitUpdate ispitUpdate = new IspitUpdate();
        ispitUpdate.setPredmetId(1);
        ispitUpdate.setIspitId(1);
        ispitUpdate.setDatum(LocalDate.now());
        ispitUpdate.setNapomena("Napomena");
        ispitUpdate.setVrsta("Usmeni");

        Mockito.when(ispitRepository.findById(any())).thenReturn(empty());

        //When
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ispitService.updateIspit(1, 1, ispitUpdate);
        });

        //Then
        assertEquals("Ne postoji ispit koji zelite urediti!", exception.getMessage());
    }

    @Test
    void updateIspit__nePostojiPredmet() {

        //Given
        final IspitId ispitId1 = new IspitId();
        ispitId1.setIspitId(1);
        ispitId1.setPredmetId(1);

        final Predmet predmet = new Predmet();
        predmet.setId(1);
        predmet.setGodina(1);
        predmet.setNaziv("Engleski jezik");

        final Ispit ispit1 = new Ispit();
        ispit1.setId(ispitId1);
        ispit1.setVrsta("Usmeni");
        ispit1.setDatum(LocalDate.now());
        ispit1.setNapomena("Napomena");
        ispit1.setPredmetid(predmet);

        final IspitUpdate ispitUpdate = new IspitUpdate();
        ispitUpdate.setPredmetId(2);
        ispitUpdate.setIspitId(1);
        ispitUpdate.setDatum(LocalDate.now());
        ispitUpdate.setNapomena("Napomena");
        ispitUpdate.setVrsta("Usmeni");

        Mockito.when(ispitRepository.findById(any())).thenReturn(of(ispit1));
        doNothing().when(ispitRepository).deleteById(any(IspitId.class));
        Mockito.when(predmetRepository.findById(any())).thenReturn(empty());

        //When
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ispitService.updateIspit(1, 1, ispitUpdate);
        });

        //Then
        assertEquals("Ne postoji predmet za koji zelite urediti ispit!", exception.getMessage());
    }

    @Test
    void updateIspit() {

        //Given
        final IspitId ispitId1 = new IspitId();
        ispitId1.setIspitId(1);
        ispitId1.setPredmetId(1);

        final Predmet predmet = new Predmet();
        predmet.setId(1);
        predmet.setGodina(1);
        predmet.setNaziv("Engleski jezik");

        final Ispit ispit1 = new Ispit();
        ispit1.setId(ispitId1);
        ispit1.setVrsta("Usmeni");
        ispit1.setDatum(LocalDate.now());
        ispit1.setNapomena("Napomena");
        ispit1.setPredmetid(predmet);

        final IspitUpdate ispitUpdate = new IspitUpdate();
        ispitUpdate.setPredmetId(1);
        ispitUpdate.setIspitId(1);
        ispitUpdate.setDatum(LocalDate.now());
        ispitUpdate.setNapomena("Napomena");
        ispitUpdate.setVrsta("Usmeni");

        when(ispitRepository.findById(any())).thenReturn(of(ispit1));
        doNothing().when(ispitRepository).deleteById(any(IspitId.class));
        when(predmetRepository.findById(any())).thenReturn(of(predmet));
        when(predmetRepository.findAll()).thenReturn(List.of(predmet));
        when(piseRepository.findByIspitIdAndPredmetId(any(), any())).thenReturn(List.of());
        when(ucenikRepostory.findUcenikeNaPredmetu(any())).thenReturn(List.of());

        //When
        final IspitDto ispitDto = ispitService.updateIspit(1, 1, ispitUpdate);


        //Then
        assertNotNull(ispitDto);
        assertEquals(ispit1.getId().getIspitId(), ispitDto.getIspitId().getIspitId());
        assertEquals(ispit1.getId().getPredmetId(), ispitDto.getIspitId().getPredmetId());
        assertEquals(ispit1.getPredmetid().getId(), ispitDto.getPredmeti().get(0).getPredmetId());
        assertEquals(1, ispitDto.getPredmeti().size());
        assertEquals(ispit1.getDatum(), ispitDto.getDatum());
        assertEquals(ispit1.getNapomena(), ispitDto.getNapomena());
        assertEquals(ispit1.getVrsta(), ispitDto.getVrsta());
        assertTrue(ispitDto.getOcjene().isEmpty());
        assertTrue(ispitDto.getUcenici().isEmpty());
    }

    @Test
    void deleteOcjena() {

        //Given
        final IspitId ispitId1 = new IspitId();
        ispitId1.setIspitId(1);
        ispitId1.setPredmetId(1);

        final Predmet predmet = new Predmet();
        predmet.setId(1);
        predmet.setGodina(1);
        predmet.setNaziv("Engleski jezik");

        final Ispit ispit1 = new Ispit();
        ispit1.setId(ispitId1);
        ispit1.setVrsta("Usmeni");
        ispit1.setDatum(LocalDate.now());
        ispit1.setNapomena("Napomena");
        ispit1.setPredmetid(predmet);
        ispitRepository.save(ispit1);


        doNothing().when(piseRepository).deleteById(any(PišeId.class));
        when(ispitRepository.findById(any(IspitId.class))).thenReturn(of(ispit1));
        when(predmetRepository.findAll()).thenReturn(List.of(predmet));
        when(piseRepository.findByIspitIdAndPredmetId(any(), any())).thenReturn(List.of());
        when(ucenikRepostory.findUcenikeNaPredmetu(any())).thenReturn(List.of());


        //When
        final IspitDto ispitDto = ispitService.deleteOcjena(1, 1, 1);

        //Then
        assertNotNull(ispitDto);
        assertTrue(ispitDto.getOcjene().isEmpty());
        assertEquals(ispit1.getId().getIspitId(), ispitDto.getIspitId().getIspitId());
        assertEquals(ispit1.getId().getPredmetId(), ispitDto.getIspitId().getPredmetId());
        assertEquals(ispit1.getPredmetid().getId(), ispitDto.getPredmeti().get(0).getPredmetId());
        assertEquals(1, ispitDto.getPredmeti().size());
        assertEquals(ispit1.getDatum(), ispitDto.getDatum());
        assertEquals(ispit1.getNapomena(), ispitDto.getNapomena());
        assertEquals(ispit1.getVrsta(), ispitDto.getVrsta());
        assertTrue(ispitDto.getOcjene().isEmpty());
        assertTrue(ispitDto.getUcenici().isEmpty());
    }

    @Test
    void createOcjena__nepopunjenaPolja() {

        //Given
        final OcjenaCreate ocjenaCreate = new OcjenaCreate();

        //When
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ispitService.createOcjena(ocjenaCreate);
        });

        //Then
        assertEquals("Nisu popunjena sva polja za kreiranje ocjene!", exception.getMessage());

    }

    @Test
    void createOcjena__nePostojiIspit() {

        //Given
        final OcjenaCreate ocjenaCreate = new OcjenaCreate();
        ocjenaCreate.setOcjena(1);
        ocjenaCreate.setNapomena("Napomena");
        ocjenaCreate.setKorisnikId(1);
        ocjenaCreate.setPredmetId(1);
        ocjenaCreate.setIspitId(1);

        when(ispitRepository.findById(any(IspitId.class))).thenReturn(empty());

        //When
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ispitService.createOcjena(ocjenaCreate);
        });

        //Then
        assertEquals("Ne postoji ispit za koji hocete unjeti ocjenu!", exception.getMessage());
    }

    @Test
    void createOcjena__ucenikNIjeNaSatu() {

        //Given
        final IspitId ispitId1 = new IspitId();
        ispitId1.setIspitId(1);
        ispitId1.setPredmetId(1);

        final Predmet predmet = new Predmet();
        predmet.setId(1);
        predmet.setGodina(1);
        predmet.setNaziv("Engleski jezik");


        final Ispit ispit1 = new Ispit();
        ispit1.setId(ispitId1);
        ispit1.setVrsta("Usmeni");
        ispit1.setDatum(LocalDate.now());
        ispit1.setNapomena("Napomena");
        ispit1.setPredmetid(predmet);

        final OcjenaCreate ocjenaCreate = new OcjenaCreate();
        ocjenaCreate.setOcjena(1);
        ocjenaCreate.setNapomena("Napomena");
        ocjenaCreate.setKorisnikId(1);
        ocjenaCreate.setPredmetId(1);
        ocjenaCreate.setIspitId(1);

        when(ispitRepository.findById(any(IspitId.class))).thenReturn(of(ispit1));
        when(izostanakRepository.existsByDatumAndKorisnikId_IdAndPredmetid_Id(any(), any(), any())).thenReturn(true);

        //When
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ispitService.createOcjena(ocjenaCreate);
        });

        //Then
        assertEquals("Ne možete unjeti ocjenu učeniku koji nije bio prisutan na satu!", exception.getMessage());
    }

    @Test
    void createOcjena__ucenik2PutaOcjenjen() {

        //Given
        final Predmet predmet = new Predmet();
        predmet.setId(1);
        predmet.setGodina(1);
        predmet.setNaziv("Engleski jezik");


        final IspitId ispitId1 = new IspitId();
        ispitId1.setIspitId(1);
        ispitId1.setPredmetId(1);

        final Ispit ispit1 = new Ispit();
        ispit1.setId(ispitId1);
        ispit1.setVrsta("Usmeni");
        ispit1.setDatum(LocalDate.now());
        ispit1.setNapomena("Napomena");
        ispit1.setPredmetid(predmet);

        final OcjenaCreate ocjenaCreate = new OcjenaCreate();
        ocjenaCreate.setOcjena(1);
        ocjenaCreate.setNapomena("Napomena");
        ocjenaCreate.setKorisnikId(1);
        ocjenaCreate.setPredmetId(1);
        ocjenaCreate.setIspitId(3);

        when(ispitRepository.findById(any(IspitId.class))).thenReturn(of(ispit1));
        when(izostanakRepository.existsByDatumAndKorisnikId_IdAndPredmetid_Id(any(), any(), any())).thenReturn(false);
        when(piseRepository.findOcjeneNaDatumZaUcenika(any(), any())).thenReturn(List.of(new Piše(), new Piše()));


        //When
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ispitService.createOcjena(ocjenaCreate);
        });

        //Then
        assertEquals("Ne možete unijeti ocjenu učeniku jer je ocijenjen već 2 puta danas!", exception.getMessage());
    }


    @Test
    void createOcjena__ucenikVecImaOcjenu() {

        //Given
        final Predmet predmet = new Predmet();
        predmet.setId(1);
        predmet.setGodina(1);
        predmet.setNaziv("Engleski jezik");

        final IspitId ispitId1 = new IspitId();
        ispitId1.setIspitId(1);
        ispitId1.setPredmetId(1);

        final Ispit ispit1 = new Ispit();
        ispit1.setId(ispitId1);
        ispit1.setVrsta("Usmeni");
        ispit1.setDatum(LocalDate.now());
        ispit1.setNapomena("Napomena");
        ispit1.setPredmetid(predmet);

        final OcjenaCreate ocjenaCreate = new OcjenaCreate();
        ocjenaCreate.setOcjena(1);
        ocjenaCreate.setNapomena("Napomena");
        ocjenaCreate.setKorisnikId(1);
        ocjenaCreate.setPredmetId(1);
        ocjenaCreate.setIspitId(1);

        when(ispitRepository.findById(any(IspitId.class))).thenReturn(of(ispit1));
        when(izostanakRepository.existsByDatumAndKorisnikId_IdAndPredmetid_Id(any(), any(), any())).thenReturn(false);
        when(piseRepository.findOcjeneNaDatumZaUcenika(any(), any())).thenReturn(List.of());
        when(piseRepository.existsById(any(PišeId.class))).thenReturn(true);


        //When
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ispitService.createOcjena(ocjenaCreate);
        });

        //Then
        assertEquals("Ucenik kojem zelite unjeti ocjenu vec ima ocjenu!", exception.getMessage());
    }

    @Test
    void createOcjena__ucenikNePostoji() {

        //Given
        final Predmet predmet = new Predmet();
        predmet.setId(1);
        predmet.setGodina(1);
        predmet.setNaziv("Engleski jezik");

        final IspitId ispitId1 = new IspitId();
        ispitId1.setIspitId(1);
        ispitId1.setPredmetId(1);

        final Ispit ispit1 = new Ispit();
        ispit1.setId(ispitId1);
        ispit1.setVrsta("Usmeni");
        ispit1.setDatum(LocalDate.now());
        ispit1.setNapomena("Napomena");
        ispit1.setPredmetid(predmet);

        final OcjenaCreate ocjenaCreate = new OcjenaCreate();
        ocjenaCreate.setOcjena(1);
        ocjenaCreate.setNapomena("Napomena");
        ocjenaCreate.setKorisnikId(1);
        ocjenaCreate.setPredmetId(1);
        ocjenaCreate.setIspitId(1);

        when(ispitRepository.findById(any(IspitId.class))).thenReturn(of(ispit1));
        when(izostanakRepository.existsByDatumAndKorisnikId_IdAndPredmetid_Id(any(), any(), any())).thenReturn(false);
        when(piseRepository.findOcjeneNaDatumZaUcenika(any(), any())).thenReturn(List.of());
        when(piseRepository.existsById(any(PišeId.class))).thenReturn(false);
        when(ucenikRepostory.findById(any())).thenReturn(empty());

        //When
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ispitService.createOcjena(ocjenaCreate);
        });

        //Then
        assertEquals("Ne postoji ucenik kojem zelite unjeti ocjenu!", exception.getMessage());
    }


    @Test
    void createOcjena() {

        //Given
        final Predmet predmet = new Predmet();
        predmet.setId(1);
        predmet.setGodina(1);
        predmet.setNaziv("Engleski jezik");

        final Korisnik korisnik = new Korisnik();
        korisnik.setId(1);
        korisnik.setIme("Ime");
        korisnik.setUsername("Username");
        korisnik.setPrezime("Prezime");
        korisnik.setOib("12345678901");
        korisnik.setSifra("Sifra");

        final Ucenik ucenik = new Ucenik();
        ucenik.setId(1);
        ucenik.setRazredid(1);
        ucenik.setKorisnik(korisnik);

        final IspitId ispitId1 = new IspitId();
        ispitId1.setIspitId(1);
        ispitId1.setPredmetId(1);

        final Ispit ispit1 = new Ispit();
        ispit1.setId(ispitId1);
        ispit1.setVrsta("Usmeni");
        ispit1.setDatum(LocalDate.now());
        ispit1.setNapomena("Napomena");
        ispit1.setPredmetid(predmet);

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


        final OcjenaCreate ocjenaCreate = new OcjenaCreate();
        ocjenaCreate.setOcjena(5);
        ocjenaCreate.setNapomena("Napomena");
        ocjenaCreate.setKorisnikId(1);
        ocjenaCreate.setPredmetId(1);
        ocjenaCreate.setIspitId(1);


        when(ispitRepository.findById(any(IspitId.class))).thenReturn(of(ispit1));
        when(izostanakRepository.existsByDatumAndKorisnikId_IdAndPredmetid_Id(any(), any(), any())).thenReturn(false);
        when(piseRepository.findOcjeneNaDatumZaUcenika(any(), any())).thenReturn(List.of());
        when(piseRepository.existsById(any(PišeId.class))).thenReturn(false);
        when(ucenikRepostory.findById(any())).thenReturn(of(ucenik));
        when(predmetRepository.findAll()).thenReturn(List.of(predmet));
        when(piseRepository.findByIspitIdAndPredmetId(any(), any())).thenReturn(List.of(pise1));
        when(ucenikRepostory.findUcenikeNaPredmetu(any())).thenReturn(List.of(ucenik));

        //When
        final IspitDto ispitDto = ispitService.createOcjena(ocjenaCreate);

        //Then
        assertFalse(ispitDto.getOcjene().isEmpty());
        final OcjenaDto ocjenaDto = ispitDto.getOcjene().get(0);
        assertEquals(ocjenaDto.getNapomena(), ocjenaCreate.getNapomena());
        assertEquals(ocjenaDto.getOcjena(), ocjenaCreate.getOcjena());
        assertEquals(ocjenaDto.getUcenici().get(0).getKorisnikId(), ocjenaCreate.getKorisnikId());
    }


    @Test
    void updateOcjena__nepopunjenaPolja() {

        //Given
        final OcjenaCreate ocjenaCreate = new OcjenaCreate();

        //When
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ispitService.updateOcjena(1, 1, 1, ocjenaCreate);
        });

        //Then
        assertEquals("Nisu popunjena sva polja za azuriranje ocjene!", exception.getMessage());

    }

    @Test
    void updateOcjena__nePostojiIspit() {

        //Given
        final OcjenaCreate ocjenaCreate = new OcjenaCreate();
        ocjenaCreate.setOcjena(1);
        ocjenaCreate.setNapomena("Napomena");
        ocjenaCreate.setKorisnikId(1);
        ocjenaCreate.setPredmetId(1);
        ocjenaCreate.setIspitId(1);

        when(ispitRepository.findById(any(IspitId.class))).thenReturn(empty());

        //When
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ispitService.updateOcjena(1, 1, 1, ocjenaCreate);
        });

        //Then
        assertEquals("Ne postoji ispit za koji hocete unjeti ocjenu!", exception.getMessage());
    }

    @Test
    void updateOcjena__ucenikNijeNaSatu() {

        //Given
        final IspitId ispitId1 = new IspitId();
        ispitId1.setIspitId(1);
        ispitId1.setPredmetId(1);

        final Predmet predmet = new Predmet();
        predmet.setId(1);
        predmet.setGodina(1);
        predmet.setNaziv("Engleski jezik");


        final Ispit ispit1 = new Ispit();
        ispit1.setId(ispitId1);
        ispit1.setVrsta("Usmeni");
        ispit1.setDatum(LocalDate.now());
        ispit1.setNapomena("Napomena");
        ispit1.setPredmetid(predmet);

        final OcjenaCreate ocjenaCreate = new OcjenaCreate();
        ocjenaCreate.setOcjena(1);
        ocjenaCreate.setNapomena("Napomena");
        ocjenaCreate.setKorisnikId(1);
        ocjenaCreate.setPredmetId(1);
        ocjenaCreate.setIspitId(1);

        when(ispitRepository.findById(any(IspitId.class))).thenReturn(of(ispit1));
        when(izostanakRepository.existsByDatumAndKorisnikId_IdAndPredmetid_Id(any(), any(), any())).thenReturn(true);


        //When
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ispitService.updateOcjena(1, 1, 1, ocjenaCreate);
        });

        //Then
        assertEquals("Ne možete unjeti ocjenu učeniku koji nije bio prisutan na satu!", exception.getMessage());
    }

    @Test
    void updateOcjena__ucenik2PutaOcjenjen() {

        //Given
        final Predmet predmet = new Predmet();
        predmet.setId(1);
        predmet.setGodina(1);
        predmet.setNaziv("Engleski jezik");


        final IspitId ispitId1 = new IspitId();
        ispitId1.setIspitId(1);
        ispitId1.setPredmetId(1);

        final Ispit ispit1 = new Ispit();
        ispit1.setId(ispitId1);
        ispit1.setVrsta("Usmeni");
        ispit1.setDatum(LocalDate.now());
        ispit1.setNapomena("Napomena");
        ispit1.setPredmetid(predmet);

        final OcjenaCreate ocjenaCreate = new OcjenaCreate();
        ocjenaCreate.setOcjena(1);
        ocjenaCreate.setNapomena("Napomena");
        ocjenaCreate.setKorisnikId(1);
        ocjenaCreate.setPredmetId(1);
        ocjenaCreate.setIspitId(3);

        when(ispitRepository.findById(any(IspitId.class))).thenReturn(of(ispit1));
        when(izostanakRepository.existsByDatumAndKorisnikId_IdAndPredmetid_Id(any(), any(), any())).thenReturn(false);
        when(piseRepository.findOcjeneNaDatumZaUcenika(any(), any())).thenReturn(List.of(new Piše(), new Piše()));

        //When
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ispitService.updateOcjena(1, 1, 1, ocjenaCreate);
        });

        //Then
        assertEquals("Ne možete unijeti ocjenu učeniku jer je ocijenjen već 2 puta danas!", exception.getMessage());
    }


    @Test
    void updateOcjena__ucenikNePostoji() {

        //Given
        final Predmet predmet = new Predmet();
        predmet.setId(1);
        predmet.setGodina(1);
        predmet.setNaziv("Engleski jezik");

        final IspitId ispitId1 = new IspitId();
        ispitId1.setIspitId(1);
        ispitId1.setPredmetId(1);

        final Ispit ispit1 = new Ispit();
        ispit1.setId(ispitId1);
        ispit1.setVrsta("Usmeni");
        ispit1.setDatum(LocalDate.now());
        ispit1.setNapomena("Napomena");
        ispit1.setPredmetid(predmet);

        final OcjenaCreate ocjenaCreate = new OcjenaCreate();
        ocjenaCreate.setOcjena(1);
        ocjenaCreate.setNapomena("Napomena");
        ocjenaCreate.setKorisnikId(1);
        ocjenaCreate.setPredmetId(1);
        ocjenaCreate.setIspitId(1);

        when(ispitRepository.findById(any(IspitId.class))).thenReturn(of(ispit1));
        when(izostanakRepository.existsByDatumAndKorisnikId_IdAndPredmetid_Id(any(), any(), any())).thenReturn(false);
        when(piseRepository.findOcjeneNaDatumZaUcenika(any(), any())).thenReturn(List.of());
        when(piseRepository.existsById(any(PišeId.class))).thenReturn(false);
        when(ucenikRepostory.findById(any())).thenReturn(empty());

        //When
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ispitService.updateOcjena(1, 1, 1, ocjenaCreate);
        });

        //Then
        assertEquals("Ne postoji ucenik kojem zelite unjeti ocjenu!", exception.getMessage());
    }


    @Test
    void updateOcjena() {

        //Given
        final Predmet predmet = new Predmet();
        predmet.setId(1);
        predmet.setGodina(1);
        predmet.setNaziv("Engleski jezik");

        final Korisnik korisnik = new Korisnik();
        korisnik.setId(1);
        korisnik.setIme("Ime");
        korisnik.setUsername("Username");
        korisnik.setPrezime("Prezime");
        korisnik.setOib("12345678901");
        korisnik.setSifra("Sifra");

        final Ucenik ucenik = new Ucenik();
        ucenik.setId(1);
        ucenik.setRazredid(1);
        ucenik.setKorisnik(korisnik);

        final IspitId ispitId1 = new IspitId();
        ispitId1.setIspitId(1);
        ispitId1.setPredmetId(1);

        final Ispit ispit1 = new Ispit();
        ispit1.setId(ispitId1);
        ispit1.setVrsta("Usmeni");
        ispit1.setDatum(LocalDate.now());
        ispit1.setNapomena("Napomena");
        ispit1.setPredmetid(predmet);

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

        final OcjenaCreate ocjenaCreate = new OcjenaCreate();
        ocjenaCreate.setOcjena(5);
        ocjenaCreate.setNapomena("Napomena");
        ocjenaCreate.setKorisnikId(1);
        ocjenaCreate.setPredmetId(1);
        ocjenaCreate.setIspitId(1);


        when(ispitRepository.findById(any(IspitId.class))).thenReturn(of(ispit1));
        when(izostanakRepository.existsByDatumAndKorisnikId_IdAndPredmetid_Id(any(), any(), any())).thenReturn(false);
        when(piseRepository.findOcjeneNaDatumZaUcenika(any(), any())).thenReturn(List.of());
        when(piseRepository.existsById(any(PišeId.class))).thenReturn(false);
        when(ucenikRepostory.findById(any())).thenReturn(of(ucenik));
        when(predmetRepository.findAll()).thenReturn(List.of(predmet));
        when(piseRepository.findByIspitIdAndPredmetId(any(), any())).thenReturn(List.of(pise1));
        when(ucenikRepostory.findUcenikeNaPredmetu(any())).thenReturn(List.of(ucenik));

        //When
        final IspitDto ispitDto = ispitService.updateOcjena(1, 1, 2, ocjenaCreate);

        //Then
        assertFalse(ispitDto.getOcjene().isEmpty());
        final OcjenaDto ocjenaDto = ispitDto.getOcjene().get(0);
        assertEquals(ocjenaDto.getNapomena(), ocjenaCreate.getNapomena());
        assertEquals(ocjenaDto.getOcjena(), ocjenaCreate.getOcjena());
        assertEquals(ocjenaDto.getUcenici().get(0).getKorisnikId(), ocjenaCreate.getKorisnikId());
    }
}
