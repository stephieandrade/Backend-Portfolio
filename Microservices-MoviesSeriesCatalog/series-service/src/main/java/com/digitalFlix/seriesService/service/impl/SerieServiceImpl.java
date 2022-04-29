package com.digitalFlix.seriesService.service.impl;

import com.digitalFlix.seriesService.model.Serie;
import com.digitalFlix.seriesService.repository.SerieRepository;
import com.digitalFlix.seriesService.service.ISerieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SerieServiceImpl implements ISerieService {

    @Autowired
    private SerieRepository serieRepository;

    //GETBYGENRE
    @Override
    public List<Serie> getSeriesByGenre(String genre) {
        return serieRepository.findAllByGenre(genre);
    }

    //GETBYID
    @Override
    public Optional<Serie> findById(String id) {
        return serieRepository.findById(id);
    }

    //POST
    @Override
    public void saveSerieService(Serie serie){
        log.info("Intentado guardar la serie");
        serieRepository.save(serie);
    }


}