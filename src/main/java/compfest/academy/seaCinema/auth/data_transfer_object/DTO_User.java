package compfest.academy.seaCinema.auth.data_transfer_object;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class DTO_User {
    @NotEmpty
    private String username;

    @NotEmpty
    @Size(min = 8)
    @Pattern(regexp = "(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&+=]).*", message = "Password must meet the complexity requirements")
    private String password;

    @NotEmpty
    private String name;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;
}

