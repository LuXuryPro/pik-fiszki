package pik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pik.JSONDao.EditProfileJsonRequest;
import pik.JSONDao.EditProfileJsonResponse;
import pik.Util.FacebookHelper;
import pik.dao.UserDao;
import pik.dto.UserInfo;
import pik.repositories.UserRepository;

import java.security.Principal;

@Controller
public class ProfileController {
    private UserDao userDao;

    @Autowired
    ProfileController(UserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping("/me")
    public String showProfile(Principal principal, Model model) {
        FacebookHelper f = new FacebookHelper(principal);
        UserInfo user = this.userDao.getById(f.getId());
        model.addAttribute("user", user);
        return "me";
    }

    @RequestMapping("/preferences")
    public String editProfile(Principal principal, Model model) {
        FacebookHelper f = new FacebookHelper(principal);
        UserInfo user = this.userDao.getById(f.getId());
        model.addAttribute("user", user);
        return "preferences";
    }
    @RequestMapping(value = "/saveUserForm")
    @ResponseBody
    public EditProfileJsonResponse addPerson(@RequestBody EditProfileJsonRequest editProfileJsonRequest) {
        String name = editProfileJsonRequest.getFirstName();
        String surname = editProfileJsonRequest.getSecondName();
        String email = editProfileJsonRequest.getEmail();
        String id = editProfileJsonRequest.getId();
        UserInfo userInfo = this.userDao.getById(id);
        userInfo.setFirstName(name);
        userInfo.setLastName(surname);
        userInfo.setEmail(email);
        this.userDao.update(userInfo);
        return new EditProfileJsonResponse("OK");
    }
}
