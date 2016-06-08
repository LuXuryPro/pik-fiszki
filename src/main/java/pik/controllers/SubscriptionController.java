package pik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pik.Util.FacebookHelper;
import pik.dto.CourseInfo;

import java.math.BigInteger;
import java.security.Principal;
import java.util.List;

@Controller
public class SubscriptionController
{
    private UserController userController;

    @Autowired
    SubscriptionController(UserController userController)
    {
        this.userController = userController;
    }

    @RequestMapping("/add-sub")
    public String addSubscription(Principal principal)
    {
        return "addSubscription";
    }

    @RequestMapping("/find-all-courses")
    public String showAllCourses()
    {

        return "allCourses";
    }

    @RequestMapping("/remove-sub")
    public String removeSubscription()
    {
        return "removeSubscription";
    }


    @RequestMapping(value = "/doAddSubstriction")
    public String doAddCourse(Principal principal, @RequestParam("id") BigInteger id)
    {
        FacebookHelper f = new FacebookHelper(principal);
        userController.addSubscription(id, f.getId());
        return "courses";
    }

    @RequestMapping(value = "/deleteSubscription")
    public String deleteSubscription(Principal principal, @RequestParam("id") BigInteger id)
    {
        FacebookHelper f = new FacebookHelper(principal);
        userController.removeSubscription(id, f.getId());
        return "courses";
    }

}
