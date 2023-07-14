package compfest.academy.seaCinema.movie.service;

import compfest.academy.seaCinema.movie.model.MovieModel;

import java.util.List;

public interface MovieService {
    List<MovieModel> getAllMovies();

    MovieModel getMovieById(Long id);

    // Tambahkan method khusus jika diperlukan
}
