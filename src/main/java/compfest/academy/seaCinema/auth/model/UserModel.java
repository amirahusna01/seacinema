package compfest.academy.seaCinema.auth.model;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;
import compfest.academy.seaCinema.ticket.model.TicketModel;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private Date birthdate;

    @Column(nullable = false)
    private long balance;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<TicketModel> ticket;
    
    // constructors, getters, and setters
}
