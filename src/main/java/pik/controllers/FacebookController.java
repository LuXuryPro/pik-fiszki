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
}
