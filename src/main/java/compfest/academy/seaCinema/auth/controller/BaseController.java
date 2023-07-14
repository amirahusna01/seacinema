package compfest.academy.seaCinema.auth.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import compfest.academy.seaCinema.auth.data_transfer_object.DTO_User;
import compfest.academy.seaCinema.auth.model.UserModel;
import compfest.academy.seaCinema.auth.service.UserService;
import compfest.academy.seaCinema.movie.model.MovieModel;
import compfest.academy.seaCinema.movie.service.MovieService;
import compfest.academy.seaCinema.ticket.service.TicketService;
import compfest.academy.seaCinema.ticket.model.TicketModel;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

@Controller
public class BaseController {
    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private MovieService movieService;

    @RequestMapping("/")
    public String index(RedirectAttributes redirectAttributes, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            redirectAttributes.addFlashAttribute("success", "User registered successfully. Please login with your credentials.");
            UserModel user = userService.getUser();
            List<TicketModel> ticketList = ticketService.getTicketsByUser(user);
            List<MovieModel> movies = movieService.getAllMovies();
            model.addAttribute("movies", movies);
            model.addAttribute("ticketList", ticketList);
            model.addAttribute("user", user);
            return "home";
        } else {
            return "auth/login";
        }
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "auth/login";
    }



    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new DTO_User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute DTO_User user, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError error : errors ) {
                System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
                return "auth/register";
            }
        }

        userService.registerUser(user);
        
        redirectAttributes.addFlashAttribute("success", "User registered successfully. Please login with your credentials.");
        return "auth/login";
    }
}


