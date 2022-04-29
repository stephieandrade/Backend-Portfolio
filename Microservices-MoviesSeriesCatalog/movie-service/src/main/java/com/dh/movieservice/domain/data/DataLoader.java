package com.dh.movieservice.domain.data;

import com.dh.movieservice.domain.model.Movie;
import com.dh.movieservice.domain.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final MovieRepository repository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        repository.save(new Movie(1, "Dark Shadows", "Terror", "https://www.netflix.com/title/70217909"));
        repository.save(new Movie(2, "Gremlins", "Terror", "https://www.netflix.com/title/562050"));
        repository.save(new Movie(3, "Minions", "Comedy", "https://www.netflix.com/title/80033394"));
        repository.save(new Movie(4, "World War Z", "Terror", "https://www.netflix.com/title/70262639"));
        repository.save(new Movie(5, "Kung-fu Panda", "Comedy", "https://www.netflix.com/title/70075480"));
        repository.save(new Movie(6, "Pacific Rim", "Action", "https://www.netflix.com/title/70267241"));
        repository.save(new Movie(7, "The Old Guard", "Action", "https://www.netflix.com/title/81038963"));
        repository.save(new Movie(8, "Pride & Prejudice", "Romance", "https://www.netflix.com/title/70032594"));
    }
}
