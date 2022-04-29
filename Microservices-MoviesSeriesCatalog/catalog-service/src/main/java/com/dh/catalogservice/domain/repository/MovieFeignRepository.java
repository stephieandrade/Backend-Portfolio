package com.dh.catalogservice.domain.repository;

import com.dh.catalogservice.domain.model.dto.dto.MovieWS;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Set;

@FeignClient(name="movie-service") //registro el cliente
public interface MovieFeignRepository {

    @GetMapping("/movies/genre/{genre}")
    Set<MovieWS> getMoviesByGenero(@RequestParam String genre);
    @PostMapping("/guardar")
    MovieWS saveMovie(@RequestBody MovieWS movie);


}
