package compfest.academy.seaCinema.movie.service;

import compfest.academy.seaCinema.movie.model.MovieModel;
import compfest.academy.seaCinema.movie.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieDb movieDb;

    @Override
    public List<MovieModel> getAllMovies() {
        return movieDb.findAll();
    }

    @Override
    public MovieModel getMovieById(Long id) {
        return movieDb.findById(id).orElse(null);
    }
}