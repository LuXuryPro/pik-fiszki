package pik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pik.Util.FacebookHelper;
import pik.dao.UserDao;
import pik.dto.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Controller
public class IndexController {
    private UserController userControler;

    IndexController() {
    }

    @Autowired
    IndexController(UserController userController) {
        this.userControler = userController;
    }

    @RequestMapping("/index")
    public String user(Principal principal, Model model) {
        FacebookHelper f = new FacebookHelper(principal);
        User user = f.getFacebookUser();
        this.userControler.addUser(user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail());
        UserInfo userInfo = this.userControler.getUser(user.getId());
        model.addAttribute("user", userInfo);
        return "index";
    }

    @RequestMapping("/")
    public String index(Principal principal, Model model) {
        if (principal == null)
            return "LoginPage";
        else {
            return "redirect:index";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

    @RequestMapping("/about")
    public String about()
    {
        return "about";
    }
}