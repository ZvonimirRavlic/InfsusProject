package com.example.controler;

import com.example.dto.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public interface Controler {

    @GetMapping("/predmeti")
    List<PredmetDto> getPredmete();

    @DeleteMapping("/predmeti/{predmetId}")
    List<PredmetDto> deletePredmet(@PathVariable Integer predmetId);

    @PostMapping(value = "/predmet", consumes = MediaType.APPLICATION_JSON_VALUE)
    List<PredmetDto> createPredmete(@RequestBody PredmetCreate predmetCreate);

    @PutMapping(value = "/predmet", consumes = MediaType.APPLICATION_JSON_VALUE)
    List<PredmetDto> updatePredmete(@RequestBody PredmetUpdate predmetUpdate);

    @GetMapping("/ispiti")
    List<IspitiResp> getIspiti();

    @GetMapping("/ispiti/{predmetId}/{ispitId}")
    IspitDto getIspit(@PathVariable Integer predmetId, @PathVariable Integer ispitId);

    @GetMapping("/ispiti/{predmetId}/{ispitId}/sljedeci")
    IspitDto getIspitSljedeci(@PathVariable Integer predmetId, @PathVariable Integer ispitId);

    @GetMapping("/ispiti/{predmetId}/{ispitId}/prosli")
    IspitDto getIspitProsli(@PathVariable Integer predmetId, @PathVariable Integer ispitId);

    @DeleteMapping("/ispiti/{predmetId}/{ispitId}")
    IspitDto deleteIspit(@PathVariable Integer predmetId, @PathVariable Integer ispitId);

    @PostMapping(value = "/ispiti", consumes = MediaType.APPLICATION_JSON_VALUE)
    IspitDto createIspit(@RequestBody IspitCreate ispitCreate);

    @PutMapping(value = "/ispiti", consumes = MediaType.APPLICATION_JSON_VALUE)
    IspitDto updateIspit(@RequestBody IspitUpdate ispitUpdate);

    @DeleteMapping("/ispiti/{predmetId}/{ispitId}/{korisnikId}")
    IspitDto deleteOcjena(@PathVariable Integer predmetId, @PathVariable Integer ispitId, @PathVariable Integer korisnikId);

    @PostMapping(value = "/ispiti/ocjena", consumes = MediaType.APPLICATION_JSON_VALUE)
    IspitDto createOcjena(@RequestBody OcjenaCreate ocjenaCreate);

    @PutMapping(value = "/ispiti/ocjena", consumes = MediaType.APPLICATION_JSON_VALUE)
    IspitDto updateOcjena(@RequestBody OcjenaCreate ocjenaCreate);
}
