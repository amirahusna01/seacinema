package compfest.academy.seaCinema.auth.service;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetailsService;
import compfest.academy.seaCinema.auth.data_transfer_object.*;
import compfest.academy.seaCinema.auth.model.UserModel;

public interface UserService extends UserDetailsService {
    void registerUser(DTO_User userDto);
    void updateBalanceMinus(UserModel user, long totalCost);
    void updateBalancePlus(UserModel user, long totalCost);
    int calculateAge(Date birthDate);
    void topUpBalance(Long id, Long amount);
    void withdrawalBalance(Long id, Long amount);
    UserModel getUserByUsername(String username);
    UserModel getUser();
    // String encrypt(String password);
}