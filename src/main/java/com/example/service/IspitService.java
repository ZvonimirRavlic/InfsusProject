package com.example.service;

import com.example.dto.IspitCreate;
import com.example.dto.IspitDto;
import com.example.dto.IspitUpdate;
import com.example.dto.OcjenaCreate;
import org.springframework.stereotype.Service;

@Service
public interface IspitService {


    IspitDto getIspit(Integer predmetId, Integer ispitId);

    IspitDto getIspitSljedeci(Integer predmetId, Integer ispitId);

    IspitDto getIspitProsli(Integer predmetId, Integer ispitId);

    IspitDto deleteIspit(Integer predmetId, Integer ispitId);


    IspitDto createIspit(IspitCreate ispitCreate);

    IspitDto updateIspit(IspitUpdate ispitUpdate);

    IspitDto deleteOcjena(Integer predmetId, Integer ispitId, Integer korisnikId);
    IspitDto createOcjena(OcjenaCreate ocjenaCreate);
    IspitDto updateOcjena(OcjenaCreate ocjenaCreate);
}
