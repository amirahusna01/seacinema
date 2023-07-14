package compfest.academy.seaCinema.ticket.repository;

import compfest.academy.seaCinema.auth.model.UserModel;
import compfest.academy.seaCinema.ticket.model.TicketModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface TicketDb extends JpaRepository<TicketModel, Long> {
    // Tambahkan metode lain jika diperlukan
    Optional<TicketModel> findById(String id);
    List<TicketModel> findByUser(UserModel user);
}
