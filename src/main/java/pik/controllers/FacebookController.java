package pik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pik.dto.UserInfo;
import pik.repositories.UserRepository;

import java.security.Principal;

@Controller
public class FacebookController {

    UserRepository users;

    FacebookController() {

    }

    @Autowired
    FacebookController(UserRepository users)
    {
        this.users = users;
    }

    @RequestMapping("/user")
    public String user(Principal principal, Model model) {
        getUserInfo(principal, model);
        return "user";
    }
    @RequestMapping("/")
    public String index(Principal principal, Model model) {
        if (principal == null)
            return "LoginPage";
        else {
            getUserInfo(principal, model);
            return "user";
        }
    }

    protected void getUserInfo(Principal principal, Model model) {
        OAuth2Authentication u = (OAuth2Authentication)principal;
        OAuth2AuthenticationDetails d = (OAuth2AuthenticationDetails)u.getDetails();
        FacebookTemplate f = new FacebookTemplate(d.getTokenValue());
        User usr = f.userOperations().getUserProfile();

        UserInfo userData;
        if(!users.exists(usr.getId())){
            userData = new UserInfo(usr.getId(),usr.getFirstName(),usr.getLastName(),usr.getEmail());
            users.save(userData);
        }
        else
            userData = users.findByuserId(usr.getId());

        model.addAttribute("facebookProfile", f.userOperations().getUserProfile());
        model.addAttribute("dataBaseProfile", userData);
    }
}
