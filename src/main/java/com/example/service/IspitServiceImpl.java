package com.example.service;

import com.example.dao.*;
import com.example.dto.*;
import com.example.entity.*;
import com.example.mapper.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class IspitServiceImpl implements IspitService {

    private final IspitRepository ispitRepository;
    private final PiseRepository piseRepository;
    private final PredmetRepository predmetRepository;
    private final UcenikRepostory ucenikRepostory;
    private final IzostanakRepository izostanakRepository;
    private final Mapper mapper;

    public IspitServiceImpl(IspitRepository ispitRepository,
                            PiseRepository piseRepository,
                            PredmetRepository predmetRepository,
                            UcenikRepostory ucenikRepostory,
                            IzostanakRepository izostanakRepository,
                            Mapper mapper) {

        this.ispitRepository = ispitRepository;
        this.piseRepository = piseRepository;
        this.predmetRepository = predmetRepository;
        this.ucenikRepostory = ucenikRepostory;
        this.izostanakRepository = izostanakRepository;
        this.mapper = mapper;
    }


    @Override
    public List<IspitiResp> getIspiti() {
        final List<Ispit> ispiti = ispitRepository.findAll();
        return mapper.map(ispiti);
    }

    @Override
    public IspitDto getIspit(Integer predmetId, Integer ispitId) {
        final IspitId ispitIdEntity = new IspitId();
        ispitIdEntity.setIspitId(ispitId);
        ispitIdEntity.setPredmetId(predmetId);

        final Ispit ispit = ispitRepository.findById(ispitIdEntity)
                .orElseThrow(() -> new RuntimeException("Ne postoji ispit sa tim id-em za ovaj predmet!"));
        final List<Piše> ocjene = piseRepository.findByIspitIdAndPredmetId(ispitId, predmetId);
        final List<Predmet> predmeti = predmetRepository.findAll();
        final List<Ucenik> uceniciNaPredmetu = ucenikRepostory.findUcenikeNaPredmetu(predmetId);
        return mapper.map(ispit, ocjene, predmeti, uceniciNaPredmetu);
    }

    @Override
    public IspitDto getIspitSljedeci(Integer predmetId, Integer ispitId) {
        final Ispit ispit = ispitRepository.findFirstById_IspitIdGreaterThanAndId_PredmetIdOrderById_IspitIdAsc(ispitId, predmetId)
                .orElseThrow(() -> new RuntimeException("Ne postoji sljedeci ispit!"));
        final List<Piše> ocjene = piseRepository.findByIspitIdAndPredmetId(ispitId, predmetId);
        final List<Predmet> predmeti = predmetRepository.findAll();
        final List<Ucenik> uceniciNaPredmetu = ucenikRepostory.findUcenikeNaPredmetu(predmetId);
        return mapper.map(ispit, ocjene, predmeti, uceniciNaPredmetu);
    }

    @Override
    public IspitDto getIspitProsli(Integer predmetId, Integer ispitId) {
        final Ispit ispit = ispitRepository.findFirstById_IspitIdLessThanAndId_PredmetIdOrderById_IspitIdDesc(ispitId, predmetId)
                .orElseThrow(() -> new RuntimeException("Ne postoji prosli ispit!"));
        final List<Piše> ocjene = piseRepository.findByIspitIdAndPredmetId(ispitId, predmetId);
        final List<Predmet> predmeti = predmetRepository.findAll();
        final List<Ucenik> uceniciNaPredmetu = ucenikRepostory.findUcenikeNaPredmetu(predmetId);
        return mapper.map(ispit, ocjene, predmeti, uceniciNaPredmetu);
    }

    @Override
    public IspitDto deleteIspit(Integer predmetId, Integer ispitId) {

        final IspitId ispitIdEntity = new IspitId();
        ispitIdEntity.setIspitId(ispitId);
        ispitIdEntity.setPredmetId(predmetId);

        ispitRepository.deleteById(ispitIdEntity);
        try {
            return getIspitProsli(predmetId, ispitId);
        } catch (Exception ignored) {
        }
        try {
            return getIspitSljedeci(predmetId, ispitId);
        } catch (Exception e) {
            throw new RuntimeException("Izbrisali ste sve ispite!");
        }
    }

    @Override
    public IspitDto createIspit(IspitCreate ispitCreate) {
        if (ispitCreate.getPredmetId() == null
                || ispitCreate.getDatum() == null
                || ispitCreate.getNapomena() == null
                || ispitCreate.getVrsta() == null) {
            throw new RuntimeException("Nisu popunjena sva polja za kreiranje ispita!");
        }
        final IspitId ispitId = new IspitId();
        ispitId.setIspitId(ispitRepository.getNextId());
        ispitId.setPredmetId(ispitCreate.getPredmetId());
        final Ispit ispit = new Ispit();
        ispit.setId(ispitId);
        ispit.setDatum(ispitCreate.getDatum());
        ispit.setNapomena(ispitCreate.getNapomena());
        ispit.setVrsta(ispitCreate.getVrsta());
        ispit.setPredmetid(predmetRepository.findById(ispitCreate.getPredmetId())
                .orElseThrow(() ->
                        new RuntimeException("Ne postoji predmet za koji zelite stvoriti ispit!")));
        ispitRepository.saveAndFlush(ispit);
        final List<Piše> ocjene = piseRepository.findByIspitIdAndPredmetId(ispitId.getIspitId(), ispitId.getPredmetId());
        final List<Predmet> predmeti = predmetRepository.findAll();
        final List<Ucenik> uceniciNaPredmetu = ucenikRepostory.findUcenikeNaPredmetu(ispitCreate.getPredmetId());
        return mapper.map(ispit, ocjene, predmeti, uceniciNaPredmetu);
    }


    @Override
    public IspitDto updateIspit(Integer predmetId, Integer ispitId1, IspitUpdate ispitUpdate) {
        if (ispitUpdate.getPredmetId() == null
                || ispitUpdate.getIspitId() == null
                || ispitUpdate.getDatum() == null
                || ispitUpdate.getNapomena() == null
                || ispitUpdate.getVrsta() == null) {
            throw new RuntimeException("Nisu popunjena sva polja za azuriranje ispita!");
        }
        final IspitId ispitIdStari = new IspitId();
        ispitIdStari.setIspitId(ispitId1);
        ispitIdStari.setPredmetId(predmetId);

        ispitRepository.findById(ispitIdStari)
                .orElseThrow(() -> new RuntimeException("Ne postoji ispit koji zelite urediti!"));

        if (!Objects.equals(predmetId, ispitUpdate.getPredmetId())) {
            final IspitId ispitId2 = new IspitId();
            ispitId2.setIspitId(ispitId1);
            ispitId2.setPredmetId(predmetId);
            ispitRepository.deleteById(ispitId2);
        }

        final IspitId ispitId = new IspitId();
        ispitId.setIspitId(ispitUpdate.getIspitId());
        ispitId.setPredmetId(ispitUpdate.getPredmetId());

        final Ispit ispit = new Ispit();
        ispit.setId(ispitId);
        ispit.setDatum(ispitUpdate.getDatum());
        ispit.setNapomena(ispitUpdate.getNapomena());
        ispit.setVrsta(ispitUpdate.getVrsta());
        ispit.setPredmetid(predmetRepository.findById(ispitUpdate.getPredmetId())
                .orElseThrow(() ->
                        new RuntimeException("Ne postoji predmet za koji zelite urediti ispit!")));
        ispitRepository.saveAndFlush(ispit);
        final List<Piše> ocjene = piseRepository.findByIspitIdAndPredmetId(ispitId.getIspitId(), ispitId.getPredmetId());
        final List<Predmet> predmeti = predmetRepository.findAll();
        final List<Ucenik> uceniciNaPredmetu = ucenikRepostory.findUcenikeNaPredmetu(ispitUpdate.getPredmetId());
        return mapper.map(ispit, ocjene, predmeti, uceniciNaPredmetu);
    }

    @Override
    public IspitDto deleteOcjena(Integer predmetId, Integer ispitId, Integer korisnikId) {
        final PišeId piseId = new PišeId();
        piseId.setPredmetId(predmetId);
        piseId.setIspitId(ispitId);
        piseId.setKorisnikId(korisnikId);
        piseRepository.deleteById(piseId);
        return getIspit(predmetId, ispitId);
    }

    @Override
    public IspitDto createOcjena(OcjenaCreate ocjenaCreate) {
        if (ocjenaCreate.getOcjena() == null
                || ocjenaCreate.getIspitId() == null
                || ocjenaCreate.getKorisnikId() == null
                || ocjenaCreate.getNapomena() == null
                || ocjenaCreate.getPredmetId() == null) {
            throw new RuntimeException("Nisu popunjena sva polja za kreiranje ocjene!");
        }

        final IspitId ispitId = new IspitId();
        ispitId.setIspitId(ocjenaCreate.getIspitId());
        ispitId.setPredmetId(ocjenaCreate.getPredmetId());
        final Ispit ispit = ispitRepository.findById(ispitId)
                .orElseThrow(() -> new RuntimeException("Ne postoji ispit za koji hocete unjeti ocjenu!"));

        if (izostanakRepository.existsByDatumAndKorisnikId_IdAndPredmetid_Id(ispit.getDatum(), ocjenaCreate.getKorisnikId(), ocjenaCreate.getPredmetId())) {
            throw new RuntimeException("Ne možete unjeti ocjenu učeniku koji nije bio prisutan na satu!");
        }
        if (piseRepository.findOcjeneNaDatumZaUcenika(ispit.getDatum(), ocjenaCreate.getKorisnikId()).size() >= 2) {
            throw new RuntimeException("Ne možete unijeti ocjenu učeniku jer je ocijenjen već 2 puta danas!");
        }

        final PišeId piseId = new PišeId();
        piseId.setPredmetId(ocjenaCreate.getPredmetId());
        piseId.setIspitId(ocjenaCreate.getIspitId());
        piseId.setKorisnikId(ocjenaCreate.getKorisnikId());

        if (piseRepository.existsById(piseId)) {
            throw new RuntimeException("Ucenik kojem zelite unjeti ocjenu vec ima ocjenu!");
        }

        final Piše ocjena = new Piše();
        ocjena.setId(piseId);
        ocjena.setKorisnikId(ucenikRepostory.findById(ocjenaCreate.getKorisnikId()).orElseThrow(() -> {
            throw new RuntimeException("Ne postoji ucenik kojem zelite unjeti ocjenu!");
        }));
        ocjena.setNapomena(ocjenaCreate.getNapomena());
        ocjena.setOcjena(ocjenaCreate.getOcjena());
        piseRepository.saveAndFlush(ocjena);
        return getIspit(ocjenaCreate.getPredmetId(), ocjenaCreate.getIspitId());
    }

    @Override
    public IspitDto updateOcjena(Integer predmetId, Integer ispitId1, Integer korisnikId, OcjenaCreate ocjenaCreate) {
        if (ocjenaCreate.getOcjena() == null
                || ocjenaCreate.getIspitId() == null
                || ocjenaCreate.getKorisnikId() == null
                || ocjenaCreate.getNapomena() == null
                || ocjenaCreate.getPredmetId() == null) {
            throw new RuntimeException("Nisu popunjena sva polja za azuriranje ocjene!");
        }

        if (korisnikId != ocjenaCreate.getKorisnikId()) {
            final PišeId piseId = new PišeId();
            piseId.setPredmetId(predmetId);
            piseId.setIspitId(ispitId1);
            piseId.setKorisnikId(korisnikId);
            piseRepository.deleteById(piseId);
        }

        final IspitId ispitId = new IspitId();
        ispitId.setIspitId(ocjenaCreate.getIspitId());
        ispitId.setPredmetId(ocjenaCreate.getPredmetId());
        final Ispit ispit = ispitRepository.findById(ispitId)
                .orElseThrow(() -> new RuntimeException("Ne postoji ispit za koji hocete unjeti ocjenu!"));

        if (izostanakRepository.existsByDatumAndKorisnikId_IdAndPredmetid_Id(ispit.getDatum(), ocjenaCreate.getKorisnikId(), ocjenaCreate.getPredmetId())) {
            throw new RuntimeException("Ne možete unjeti ocjenu učeniku koji nije bio prisutan na satu!");
        }
        if (piseRepository.findOcjeneNaDatumZaUcenika(ispit.getDatum(), ocjenaCreate.getKorisnikId()).size() >= 2) {
            throw new RuntimeException("Ne možete unijeti ocjenu učeniku jer je ocijenjen već 2 puta danas!");
        }

        final PišeId piseId = new PišeId();
        piseId.setPredmetId(ocjenaCreate.getPredmetId());
        piseId.setIspitId(ocjenaCreate.getIspitId());
        piseId.setKorisnikId(ocjenaCreate.getKorisnikId());


        final Piše ocjena = new Piše();
        ocjena.setId(piseId);
        ocjena.setKorisnikId(ucenikRepostory.findById(ocjenaCreate.getKorisnikId()).orElseThrow(() -> {
            throw new RuntimeException("Ne postoji ucenik kojem zelite unjeti ocjenu!");
        }));
        ocjena.setNapomena(ocjenaCreate.getNapomena());
        ocjena.setOcjena(ocjenaCreate.getOcjena());
        piseRepository.saveAndFlush(ocjena);
        return getIspit(ocjenaCreate.getPredmetId(), ocjenaCreate.getIspitId());
    }
}
