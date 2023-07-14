package compfest.academy.seaCinema.ticket.service;

import compfest.academy.seaCinema.auth.model.UserModel;
import compfest.academy.seaCinema.movie.model.MovieModel;
import compfest.academy.seaCinema.ticket.model.TicketModel;

import java.util.List;

public interface TicketService {
    void bookTicket(UserModel user, MovieModel movie, List<Integer> seatNumbers);
    void cancelTicket(Long ticketId, UserModel user);
    List<TicketModel> getTicketsByUser(UserModel user);
    TicketModel getTicketById(Long id);
}
