package com.example.service;

import com.example.dto.PredmetCreate;
import com.example.dto.PredmetDto;
import com.example.dto.PredmetUpdate;

import java.util.List;

public interface PredmetService {

    List<PredmetDto> getPredmete();
    List<PredmetDto> deletePredmet(Integer predmetId);
    List<PredmetDto> createPredmet(PredmetCreate predmetCreate);
    List<PredmetDto> updatePredmet(PredmetUpdate predmetUpdate);

}
