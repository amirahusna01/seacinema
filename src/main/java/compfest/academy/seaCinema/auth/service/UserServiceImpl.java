package compfest.academy.seaCinema.auth.service;

import compfest.academy.seaCinema.auth.repository.UserDb;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import compfest.academy.seaCinema.auth.data_transfer_object.*;
import compfest.academy.seaCinema.auth.model.UserModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.Authentication;

@Service
@Configuration
@EnableWebSecurity
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDb userDb;

    @Override
    public void registerUser(DTO_User userDto) {

        // Validasi password complexity
        if (!isPasswordComplex(userDto.getPassword())) {
            throw new IllegalArgumentException("Password does not meet the complexity requirements");
        }

        // Create user entity
        UserModel user = new UserModel();
        user.setUsername(userDto.getUsername());
        user.setPassword(encrypt(userDto.getPassword()));
        user.setName(userDto.getName());
        user.setBirthdate(userDto.getBirthdate());
        user.setBalance(0);

        // Save user to database
        userDb.save(user);
    }

    // Validasi password complexity
    private boolean isPasswordComplex(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&+=])(?=.*[a-zA-Z0-9]).{8,}$";
        return password.matches(regex);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userDb.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.emptyList()
        );
    }

    @Override
    public void updateBalanceMinus(UserModel user, long totalCost){
        long newBalance = user.getBalance() - totalCost;
        user.setBalance(newBalance);
        userDb.save(user);
    }

    @Override
    public void updateBalancePlus(UserModel user, long totalCost){
        long newBalance = user.getBalance() + totalCost;
        user.setBalance(newBalance);
        userDb.save(user);
    }

    @Override
    public int calculateAge(Date birthDate) {
        Calendar birthCalendar = Calendar.getInstance();
        birthCalendar.setTime(birthDate);
        Calendar currentCalendar = Calendar.getInstance();
        int years = currentCalendar.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR);
        if (currentCalendar.get(Calendar.MONTH) < birthCalendar.get(Calendar.MONTH)
                || (currentCalendar.get(Calendar.MONTH) == birthCalendar.get(Calendar.MONTH)
                && currentCalendar.get(Calendar.DAY_OF_MONTH) < birthCalendar.get(Calendar.DAY_OF_MONTH))) {
            years--;
        }
        return years;
    }

    @Override
    public UserModel getUserByUsername(String username) {
        return userDb.findByUsername(username).orElse(null);
    }

    @Override
    public void topUpBalance(Long id, Long amount) {
        UserModel profile = userDb.findById(id).orElseThrow(() -> new IllegalArgumentException("Profile not found"));
        profile.setBalance(profile.getBalance() + amount);
        userDb.save(profile);
    }

    @Override
    public void withdrawalBalance(Long id, Long amount) {
        UserModel profile = userDb.findById(id).orElseThrow(() -> new IllegalArgumentException("Profile not found"));
        double maxWithdrawal = Math.min(profile.getBalance(), 500000);
        double withdrawalAmount = Math.min(maxWithdrawal, amount);
        profile.setBalance(Double.valueOf(profile.getBalance() - withdrawalAmount).longValue());
        userDb.save(profile);
    }

    @Override
    public UserModel getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return getUserByUsername(username);
    }


    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }


}
