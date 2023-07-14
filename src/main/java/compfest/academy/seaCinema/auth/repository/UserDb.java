package compfest.academy.seaCinema.auth.repository;

import compfest.academy.seaCinema.auth.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserDb extends JpaRepository<UserModel, String>{
    // JPA
    Optional<UserModel> findById(Long code);
    Optional<UserModel> findByUsername(String username);
}

