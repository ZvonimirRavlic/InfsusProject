package com.example.service;

import com.example.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IspitService {


    List<IspitiResp> getIspiti();

    IspitDto getIspit(Integer predmetId, Integer ispitId);

    IspitDto getIspitSljedeci(Integer predmetId, Integer ispitId);

    IspitDto getIspitProsli(Integer predmetId, Integer ispitId);

    IspitDto deleteIspit(Integer predmetId, Integer ispitId);


    IspitDto createIspit(IspitCreate ispitCreate);

    IspitDto updateIspit(Integer predmetId, Integer ispitId, IspitUpdate ispitUpdate);

    IspitDto deleteOcjena(Integer predmetId, Integer ispitId, Integer korisnikId);

    IspitDto createOcjena(OcjenaCreate ocjenaCreate);

    IspitDto updateOcjena(Integer predmetId, Integer ispitId, Integer korisnikId, OcjenaCreate ocjenaCreate);
}
