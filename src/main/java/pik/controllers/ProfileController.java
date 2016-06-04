package pik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pik.Util.FacebookHelper;
import pik.dto.UserInfo;
import pik.repositories.UserRepository;

import java.security.Principal;

@Controller
public class ProfileController {
    private UserRepository userRepository;
    @Autowired
    ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @RequestMapping("/me")
    public String showProfile(Principal principal, Model model) {
        FacebookHelper f = new FacebookHelper(principal);
        UserInfo user = this.userRepository.findByuserId(f.getId());
        model.addAttribute("user", user);
        return "me";
    }
    @RequestMapping("/preferences")
    public String showProfile(Principal principal, Model model) {
        FacebookHelper f = new FacebookHelper(principal);
        UserInfo user = this.userRepository.findByuserId(f.getId());
        model.addAttribute("user", user);
        return "preferences";
    }
}
