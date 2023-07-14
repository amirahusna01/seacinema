package compfest.academy.seaCinema.movie.controller;

import compfest.academy.seaCinema.movie.model.MovieModel;
import compfest.academy.seaCinema.movie.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@Controller
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping("/movies")
    public String getAllMovies(Model model) {
        List<MovieModel> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        ObjectMapper objectMapper = new ObjectMapper();
        String moviesJson;
        try {
            moviesJson = objectMapper.writeValueAsString(movies);
        } catch (JsonProcessingException e) {
            moviesJson = "[]";
        }
        model.addAttribute("moviesJson", moviesJson);
        return "movies/movies";
    }

    @GetMapping("/movie/{id}")
    public String getMovieById(@PathVariable Long id, Model model) {
        MovieModel movie = movieService.getMovieById(id);
        model.addAttribute("movie", movie);
        return "movies/movie";
    }
}
