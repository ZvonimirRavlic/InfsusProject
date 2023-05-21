package com.example.app;

import com.example.AppApplication;
import com.example.dto.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest(classes = AppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class IntegrationtestsOnH2 {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    @SqlGroup({
            @Sql(value = "classpath:dropData.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:user-data.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    public void testIspit() {

        final IspitCreate ispitCreate = new IspitCreate();
        ispitCreate.setPredmetId(2);
        ispitCreate.setDatum(LocalDate.now());
        ispitCreate.setNapomena("Napomena");
        ispitCreate.setVrsta("Usmeni");

        ResponseEntity<IspitDto> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/ispiti", ispitCreate, IspitDto.class);
        final IspitDto noviIspit = responseEntity.getBody();

        ResponseEntity<IspitDto[]> responseEntity1 = this.restTemplate
                .getForEntity("http://localhost:" + port + "/ispiti", IspitDto[].class);
        assertEquals(200, responseEntity1.getStatusCode().value());

        this.restTemplate.delete("http://localhost:" + port + "/ispiti/" + noviIspit.getIspitId().getPredmetId() + "/" + noviIspit.getIspitId().getIspitId());

    }


    @SqlGroup({
            @Sql(value = "classpath:dropData.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:user-data.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    @Test
    public void testCreateAndGetAndDeleteIspit() {
        final IspitCreate ispitCreate = new IspitCreate();
        ispitCreate.setPredmetId(2);
        ispitCreate.setDatum(LocalDate.now());
        ispitCreate.setNapomena("Napomena");
        ispitCreate.setVrsta("Usmeni");

        ResponseEntity<IspitDto> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/ispiti", ispitCreate, IspitDto.class);
        final IspitDto noviIspit = responseEntity.getBody();
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(2, noviIspit.getPredmeti().get(0).getPredmetId());
        assertEquals(LocalDate.now(), noviIspit.getDatum());
        assertEquals("Napomena", noviIspit.getNapomena());
        assertEquals("Usmeni", noviIspit.getVrsta());

        final IspitUpdate ispitUpdate = new IspitUpdate();
        ispitUpdate.setPredmetId(noviIspit.getIspitId().getPredmetId());
        ispitUpdate.setIspitId(noviIspit.getIspitId().getIspitId());
        ispitUpdate.setDatum(LocalDate.now());
        ispitUpdate.setNapomena("Napomena");
        ispitUpdate.setVrsta("Pismeni");

        this.restTemplate.put("http://localhost:" + port + "/ispiti/" + noviIspit.getIspitId().getPredmetId() + "/" + noviIspit.getIspitId().getIspitId(), ispitUpdate, IspitDto.class);

        ResponseEntity<IspitDto> updateEntity = this.restTemplate
                .getForEntity("http://localhost:" + port + "/ispiti/" + noviIspit.getIspitId().getPredmetId() + "/" + noviIspit.getIspitId().getIspitId(), IspitDto.class);

        assertEquals("Pismeni", updateEntity.getBody().getVrsta());

        this.restTemplate.delete("http://localhost:" + port + "/ispiti/" + noviIspit.getIspitId().getPredmetId() + "/" + noviIspit.getIspitId().getIspitId());

        ResponseEntity<IspitDto> deleted = this.restTemplate
                .getForEntity("http://localhost:" + port + "/ispiti/" + noviIspit.getIspitId().getPredmetId() + "/" + noviIspit.getIspitId().getIspitId(), IspitDto.class);

        assertEquals(500, deleted.getStatusCode().value());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:dropData.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:user-data.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    public void testOcjene() {
        final IspitCreate ispitCreate = new IspitCreate();
        ispitCreate.setPredmetId(2);
        ispitCreate.setDatum(LocalDate.now());
        ispitCreate.setNapomena("Napomena");
        ispitCreate.setVrsta("Usmeni");

        ResponseEntity<IspitDto> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/ispiti", ispitCreate, IspitDto.class);
        final IspitDto noviIspit = responseEntity.getBody();

        final OcjenaCreate ocjenaCreate = new OcjenaCreate();
        ocjenaCreate.setOcjena(1);
        ocjenaCreate.setNapomena("Napomena");
        ocjenaCreate.setKorisnikId(1);
        ocjenaCreate.setPredmetId(2);
        ocjenaCreate.setIspitId(noviIspit.getIspitId().getIspitId());

        ResponseEntity<IspitDto> ispitSOcjenom = this.restTemplate
                .postForEntity("http://localhost:" + port + "/ispiti/ocjena", ocjenaCreate, IspitDto.class);

        final OcjenaDto ocjenaDto = ispitSOcjenom.getBody().ocjene.get(0);
        assertEquals(200, ispitSOcjenom.getStatusCode().value());
        assertEquals(1, ocjenaDto.getOcjena());
        assertEquals("Napomena", ocjenaDto.getNapomena());

        ocjenaCreate.setOcjena(5);

        this.restTemplate.put("http://localhost:" + port + "/ispiti/ocjena/" + noviIspit.getIspitId().getPredmetId() + "/" + noviIspit.getIspitId().getIspitId() + "/" + ocjenaCreate.getKorisnikId(), ocjenaCreate, IspitDto.class);

        ResponseEntity<IspitDto> updateEntity = this.restTemplate
                .getForEntity("http://localhost:" + port + "/ispiti/" + noviIspit.getIspitId().getPredmetId() + "/" + noviIspit.getIspitId().getIspitId(), IspitDto.class);

        assertEquals(5, updateEntity.getBody().ocjene.get(0).getOcjena());

        this.restTemplate.delete("http://localhost:" + port + "/ispiti/" + noviIspit.getIspitId().getPredmetId() + "/" + noviIspit.getIspitId().getIspitId());

        ResponseEntity<IspitDto> deleted = this.restTemplate
                .getForEntity("http://localhost:" + port + "/ispiti/" + noviIspit.getIspitId().getPredmetId() + "/" + noviIspit.getIspitId().getIspitId(), IspitDto.class);

        assertEquals(500, deleted.getStatusCode().value());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:dropData.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:user-data.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    public void testPredmeti() {
        ResponseEntity<PredmetDto[]> responseEntity = this.restTemplate
                .getForEntity("http://localhost:" + port + "/predmeti", PredmetDto[].class);
        assertEquals(200, responseEntity.getStatusCode().value());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:dropData.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:user-data.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    public void testPredmetCreateAndUpdateAndDelete() {

        final PredmetCreate predmetCreate = new PredmetCreate();
        predmetCreate.setNaziv("zzzzzzzzzzzz");
        predmetCreate.setGodina(1);
        ResponseEntity<PredmetDto[]> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/predmet", predmetCreate, PredmetDto[].class);
        assertEquals(200, responseEntity.getStatusCode().value());
        List<PredmetDto> lista = Arrays.stream(responseEntity.getBody()).toList();
        PredmetDto predmetDto = lista.stream().filter(predmet -> predmet.getNazivGodina().equals("zzzzzzzzzzzz 1")).findFirst().orElse(null);


        final PredmetUpdate predmetUpdate = new PredmetUpdate();
        predmetUpdate.setPredmetId(predmetDto.getPredmetId());
        predmetUpdate.setNaziv("zzzzzzzzzzzz");
        predmetUpdate.setGodina(3);
        this.restTemplate.put("http://localhost:" + port + "/predmet", predmetUpdate, PredmetDto[].class);

        ResponseEntity<PredmetDto[]> updateResponseEntity = this.restTemplate
                .getForEntity("http://localhost:" + port + "/predmeti", PredmetDto[].class);
        assertEquals(200, responseEntity.getStatusCode().value());

        List<PredmetDto> listaUpdate = Arrays.stream(updateResponseEntity.getBody()).toList();
        PredmetDto predmetDtoUpdate = listaUpdate.stream().filter(predmet -> predmet.getNazivGodina().equals("zzzzzzzzzzzz 3")).findFirst().orElse(null);

        assertEquals(3, predmetDtoUpdate.getGodina());

        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(predmetDto.getPredmetId()));
        this.restTemplate.delete("http://localhost:" + port + "/predmeti/{id}", params);

        ResponseEntity<PredmetDto[]> deleteResponseEntity = this.restTemplate
                .getForEntity("http://localhost:" + port + "/predmeti", PredmetDto[].class);
        assertEquals(200, deleteResponseEntity.getStatusCode().value());

    }

}
