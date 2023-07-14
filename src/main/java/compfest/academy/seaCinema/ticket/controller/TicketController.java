package compfest.academy.seaCinema.ticket.controller;

import compfest.academy.seaCinema.auth.model.UserModel;
import compfest.academy.seaCinema.auth.service.*;
import compfest.academy.seaCinema.movie.model.MovieModel;
import compfest.academy.seaCinema.movie.service.MovieService;
import compfest.academy.seaCinema.ticket.model.TicketModel;
import compfest.academy.seaCinema.ticket.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

@Controller
public class TicketController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    @GetMapping("/movies/{movieId}/booking")
    public String showBookingPage(@PathVariable("movieId") Long movieId, Model model, Principal principal) {
        MovieModel movie = movieService.getMovieById(movieId);
        UserModel user = userService.getUser();
        List<TicketModel> userTickets = ticketService.getTicketsByUser(user);

        Set<Integer> bookedSeats = new HashSet<>();
        for (TicketModel ticket : userTickets) {
            if (ticket.getMovie().getId().equals(movieId)) {
                bookedSeats.addAll(ticket.getSeat());
            }
        }

        model.addAttribute("movie", movie);
        model.addAttribute("user", user);
        model.addAttribute("bookedSeats", bookedSeats);
        return "ticket/booking";
    }

    @PostMapping("/movies/{movieId}/booking")
    public String bookTickets(@PathVariable("movieId") Long movieId, @RequestParam("seatNumbers") List<Integer> seatNumbers,
                            Principal principal, RedirectAttributes redirectAttributes) {
        // Mendapatkan data tiket, pengguna, dan saldo
        MovieModel movie = movieService.getMovieById(movieId);
        UserModel user = userService.getUser();
        int selectedCount = seatNumbers.size();
        int ticketPrice = movie.getTicketPrice();
        int totalCost = selectedCount * ticketPrice;

        // Pengecekan saldo
        if (user.getBalance() < totalCost) {
            redirectAttributes.addFlashAttribute("errorMessage", "Insufficient balance! Please top up your balance.");
            return "redirect:/movies/{movieId}/booking";
        }

        // Pengecekan usia pengguna
        int ageRating = movie.getAgeRating();
        int userAge = userService.calculateAge(user.getBirthdate());
        if (userAge < ageRating) {
            redirectAttributes.addFlashAttribute("errorMessage", "You are not old enough to book this movie!");
            return "redirect:/movies/{movieId}/booking";
        }

        ticketService.bookTicket(user, movie, seatNumbers);

        redirectAttributes.addFlashAttribute("successMessage", "Ticket booked successfully!");
        return "redirect:/tickets";
    }

    @GetMapping("/tickets")
    public String ticketHistory(Model model, Principal principal) {
        UserModel user = userService.getUser();
        List<TicketModel> ticketList = ticketService.getTicketsByUser(user);
        model.addAttribute("ticketList", ticketList);
        return "ticket/history";
    }



    @PostMapping("/tickets/{ticketId}/cancel")
    public String cancelTicket(@PathVariable("ticketId") Long ticketId) {
        UserModel user = userService.getUser();
        ticketService.cancelTicket(ticketId, user);
        return "redirect:/tickets";
    }

    @GetMapping("/tickets/{ticketId}")
    public String showTicketDetail(@PathVariable("ticketId") Long ticketId, Model model) {
        TicketModel ticket = ticketService.getTicketById(ticketId);
        model.addAttribute("ticket", ticket);
        return "ticket/detail";
    }
}

