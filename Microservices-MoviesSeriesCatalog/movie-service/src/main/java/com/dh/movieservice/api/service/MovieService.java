package com.dh.movieservice.api.service;

import com.dh.movieservice.domain.model.Movie;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MovieService {
	Set<Movie> getListByGenre(String genre);
	Optional<Movie> findById(Integer id);
	Movie save(Movie movie);
	void delete(Integer id);
}
