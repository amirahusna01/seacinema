package compfest.academy.seaCinema.ticket.service;

import compfest.academy.seaCinema.auth.model.UserModel;
import compfest.academy.seaCinema.auth.service.UserService;
import compfest.academy.seaCinema.movie.model.MovieModel;
import compfest.academy.seaCinema.ticket.model.TicketModel;
import compfest.academy.seaCinema.ticket.repository.TicketDb;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Date;
@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    private TicketDb ticketDb;

    @Autowired
    private UserService userService;

    @Override
    public void bookTicket(UserModel user, MovieModel movie, List<Integer> seatNumbers) {
        Date currentDate = new Date();
        TicketModel ticket = new TicketModel();
        ticket.setBookingDate(currentDate);
        ticket.setUser(user);
        ticket.setMovie(movie);
        ticket.setSeat(seatNumbers);
        ticket.setTotal(seatNumbers.size()*movie.getTicketPrice());
        ticketDb.save(ticket);
        userService.updateBalanceMinus(user, seatNumbers.size()*movie.getTicketPrice());
        
    }

    @Override
    public void cancelTicket(Long ticketId, UserModel user) {
        TicketModel ticket = getTicketById(ticketId);
        userService.updateBalancePlus(user, ticket.getTotal());
        ticketDb.delete(ticket);
    }

    @Override
    public List<TicketModel> getTicketsByUser(UserModel user) {
        return ticketDb.findByUser(user);
    }

    @Override
    public TicketModel getTicketById(Long id) {
        return ticketDb.findById(id).orElse(null);
    }
}