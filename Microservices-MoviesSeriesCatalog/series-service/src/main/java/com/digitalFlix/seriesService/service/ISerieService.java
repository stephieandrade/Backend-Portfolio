package com.digitalFlix.seriesService.service;

import com.digitalFlix.seriesService.model.Serie;

import java.util.List;
import java.util.Optional;

public interface ISerieService {
    List<Serie> getSeriesByGenre(String genre);
    Optional<Serie> findById(String id);
    void saveSerieService(Serie serie);
}
