package com.example.controler;

import com.example.dto.*;
import com.example.service.IspitService;
import com.example.service.PredmetService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ControlerImpl implements Controler {


    private final PredmetService predmetService;
    private final IspitService ispitService;

    public ControlerImpl(PredmetService predmetService,
                         IspitService ispitService) {
        this.predmetService = predmetService;
        this.ispitService = ispitService;
    }

    @Override
    public List<PredmetDto> getPredmete() {
        return predmetService.getPredmete();
    }

    @Override
    public List<PredmetDto> deletePredmet(Integer predmetId) {
        return predmetService.deletePredmet(predmetId);
    }

    @Override
    public List<PredmetDto> createPredmete(PredmetCreate predmetCreate) {
        return predmetService.createPredmet(predmetCreate);
    }

    @Override
    public List<PredmetDto> updatePredmete(PredmetUpdate predmetUpdate) {
        return predmetService.updatePredmet(predmetUpdate);
    }

    @Override
    public List<IspitiResp> getIspiti() {
        return ispitService.getIspiti();
    }

    @Override
    public IspitDto getIspit(Integer predmetId, Integer ispitId) {
        return ispitService.getIspit(predmetId, ispitId);
    }

    @Override
    public IspitDto getIspitSljedeci(Integer predmetId, Integer ispitId) {
        return ispitService.getIspitSljedeci(predmetId, ispitId);
    }

    @Override
    public IspitDto getIspitProsli(Integer predmetId, Integer ispitId) {
        return ispitService.getIspitProsli(predmetId, ispitId);
    }

    @Override
    public IspitDto deleteIspit(Integer predmetId, Integer ispitId) {
        return ispitService.deleteIspit(predmetId, ispitId);
    }

    @Override
    public IspitDto createIspit(IspitCreate ispitCreate) {
        return ispitService.createIspit(ispitCreate);
    }

    @Override
    public IspitDto updateIspit(Integer predmetId, Integer ispitId, IspitUpdate ispitUpdate) {
        return ispitService.updateIspit(predmetId, ispitId, ispitUpdate);
    }

    @Override
    public IspitDto deleteOcjena(Integer predmetId, Integer ispitId, Integer korisnikId) {
        return ispitService.deleteOcjena(predmetId, ispitId, korisnikId);
    }

    @Override
    public IspitDto createOcjena(OcjenaCreate ocjenaCreate) {
        return ispitService.createOcjena(ocjenaCreate);
    }

    @Override
    public IspitDto updateOcjena(Integer predmetId, Integer ispitId, Integer korisnikId, OcjenaCreate ocjenaCreate) {
        return ispitService.updateOcjena(predmetId, ispitId, korisnikId, ocjenaCreate);
    }

}
