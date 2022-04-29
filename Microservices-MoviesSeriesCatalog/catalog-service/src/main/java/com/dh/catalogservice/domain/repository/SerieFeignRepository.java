package com.dh.catalogservice.domain.repository;

import com.dh.catalogservice.domain.model.dto.dto.MovieWS;
import com.dh.catalogservice.domain.model.dto.dto.SerieWS;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@FeignClient(name="series-service") //registro el cliente
public interface SerieFeignRepository {
    @GetMapping("/series/{genre}")
    Set<SerieWS> getSeriesByGenre(@RequestParam String genre);
}
