package com.example.app;

import com.example.dao.PredmetRepository;
import com.example.dto.PredmetCreate;
import com.example.dto.PredmetDto;
import com.example.dto.PredmetUpdate;
import com.example.entity.Predmet;
import com.example.service.PredmetService;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class PredmetServiceTests {

    @Autowired
    private PredmetService predmetService;

    @MockBean
    private PredmetRepository predmetRepository;

    @BeforeEach
    void resetMocks() {
        Mockito.reset(predmetRepository);
    }

    @Test
    void getPredmete() {

        //Given
        final Predmet predmet = new Predmet();
        predmet.setId(1);
        predmet.setGodina(1);
        predmet.setNaziv("Naziv");

        Mockito.when(predmetRepository.findAll()).thenReturn(List.of(predmet));

        //When
        final List<PredmetDto> predmetDto = predmetService.getPredmete();

        //Then
        assertEquals(1, predmetDto.size());
        assertEquals(1, predmetDto.get(0).getPredmetId());
        assertEquals(1, predmetDto.get(0).getGodina());
        assertEquals("Naziv", predmetDto.get(0).getNaziv());
        assertEquals("Naziv 1", predmetDto.get(0).getNazivGodina());

    }

    @Test
    void deletePredmet() {

        //Given
        final Predmet predmet = new Predmet();
        predmet.setId(1);
        predmet.setGodina(1);
        predmet.setNaziv("Naziv");

        Mockito.when(predmetRepository.findAll()).thenReturn(List.of(predmet));
        Mockito.doNothing().when(predmetRepository).deleteById(Mockito.any());
        //When
        final List<PredmetDto> predmetDto = predmetService.deletePredmet(2);

        //Then
        assertEquals(1, predmetDto.size());
        assertEquals(1, predmetDto.get(0).getPredmetId());
        assertEquals(1, predmetDto.get(0).getGodina());
        assertEquals("Naziv", predmetDto.get(0).getNaziv());
        assertEquals("Naziv 1", predmetDto.get(0).getNazivGodina());

    }

    @Test
    void createPredmet__nepopunjenaPolja() {

        //Given
        final PredmetCreate predmet = new PredmetCreate();

        //When
        Exception exception = assertThrows(RuntimeException.class, () -> {
            predmetService.createPredmet(predmet);
        });

        //Then
        assertEquals("Nisu popunjena sva polja za kreiranje predmeta!", exception.getMessage());

    }

    @Test
    void createPredmet() {

        //Given
        final PredmetCreate predmetCreate = new PredmetCreate();
        predmetCreate.setGodina(1);
        predmetCreate.setNaziv("Naziv");

        final Predmet predmet = new Predmet();
        predmet.setId(1);
        predmet.setGodina(1);
        predmet.setNaziv("Naziv");

        Mockito.when(predmetRepository.findAll()).thenReturn(List.of(predmet));
        //When
        final List<PredmetDto> predmetDto = predmetService.createPredmet(predmetCreate);

        //Then
        assertEquals(1, predmetDto.size());
        assertEquals(1, predmetDto.get(0).getPredmetId());
        assertEquals(1, predmetDto.get(0).getGodina());
        assertEquals("Naziv", predmetDto.get(0).getNaziv());
        assertEquals("Naziv 1", predmetDto.get(0).getNazivGodina());

    }


    @Test
    void updatePredmet__nepopunjenaPolja() {

        //Given
        final PredmetUpdate predmet = new PredmetUpdate();

        //When
        Exception exception = assertThrows(RuntimeException.class, () -> {
            predmetService.updatePredmet(predmet);
        });

        //Then
        assertEquals("Nisu popunjena sva polja za azuriranje predmeta!", exception.getMessage());

    }

    @Test
    void updatePredmet() {

        //Given
        final PredmetUpdate predmetUpdate = new PredmetUpdate();
        predmetUpdate.setPredmetId(1);
        predmetUpdate.setGodina(1);
        predmetUpdate.setNaziv("Naziv");

        final Predmet predmet = new Predmet();
        predmet.setId(1);
        predmet.setGodina(1);
        predmet.setNaziv("Naziv");

        Mockito.when(predmetRepository.findAll()).thenReturn(List.of(predmet));
        //When
        final List<PredmetDto> predmetDto = predmetService.updatePredmet(predmetUpdate);

        //Then
        assertEquals(1, predmetDto.size());
        assertEquals(1, predmetDto.get(0).getPredmetId());
        assertEquals(1, predmetDto.get(0).getGodina());
        assertEquals("Naziv", predmetDto.get(0).getNaziv());
        assertEquals("Naziv 1", predmetDto.get(0).getNazivGodina());

    }
}
