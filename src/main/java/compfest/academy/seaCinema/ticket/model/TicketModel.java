package compfest.academy.seaCinema.ticket.model;

import compfest.academy.seaCinema.auth.model.UserModel;
import compfest.academy.seaCinema.movie.model.MovieModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;

import java.util.List;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TicketModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private MovieModel movie;

    @NotNull
    @Column(nullable = false)
    private int total;

    @NotNull
    @Column(nullable = false)
    private Date bookingDate;

    @ElementCollection
    @CollectionTable(name = "ticket_seat", joinColumns = @JoinColumn(name = "ticket_id"))
    @Cascade(org.hibernate.annotations.CascadeType.ALL) 
    @Column(name = "seat")
    private List<Integer> seat;

}