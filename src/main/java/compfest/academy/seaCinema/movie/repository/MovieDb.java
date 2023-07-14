package compfest.academy.seaCinema.movie.repository;

import compfest.academy.seaCinema.auth.model.UserModel;
import compfest.academy.seaCinema.movie.model.MovieModel;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieDb extends JpaRepository<MovieModel, Long> {
    // Tambahkan method khusus jika diperlukan
    Optional<UserModel> findById(String id);
    Optional<UserModel> findByTitle(String title);
}