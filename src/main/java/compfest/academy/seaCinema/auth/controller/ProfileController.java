package compfest.academy.seaCinema.auth.controller;

import compfest.academy.seaCinema.auth.model.UserModel;

import compfest.academy.seaCinema.auth.service.*;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

import java.security.Principal;

@Controller
public class ProfileController {
    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String getProfile(Principal principal, Model model) {
        UserModel profile = userService.getUser();
        model.addAttribute("profile", profile);
        model.addAttribute("id", profile.getId());
        return "profile/profile";
    }

    @PostMapping("/profile/topup")
    public String topUpBalance(@RequestParam("amount") Long amount) {
        UserModel profile = userService.getUser();
        userService.topUpBalance(profile.getId(), amount);
        return "redirect:/profile";
    }

    @PostMapping("/profile/withdrawal")
    public String withdrawalBalance(@RequestParam("amount") Long amount) {
        UserModel profile = userService.getUser();
        userService.withdrawalBalance(profile.getId(), amount);
        return "redirect:/profile";
    }
}
