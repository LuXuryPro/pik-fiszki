package pik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pik.Util.FacebookHelper;
import pik.dto.CourseInfo;
import pik.dto.QuestionInfo;

import java.math.BigInteger;
import java.security.Principal;
import java.util.List;

@Controller
public class SubscriptionController
{
    private UserController userController;
    private CourseController courseController;

    @Autowired
    SubscriptionController(UserController userController, CourseController courseController)
    {
        this.userController = userController;
        this.courseController = courseController;
    }

    @RequestMapping("/add-sub")
    public String addSubscription(Principal principal)
    {
        return "addSubscription";
    }

    @RequestMapping("/find-all-courses")
    public String showAllCourses(Model model, Principal principal)
    {
        FacebookHelper f = new FacebookHelper(principal);
        List<CourseInfo> allCourses = courseController.getUnsubscribedCourses(f.getId());
        model.addAttribute("allCourses", allCourses);
        return "allCourses";
    }

    @RequestMapping(value = "/remove-sub/{courseID}")
    public String removeSubscription(@PathVariable(value = "courseID") BigInteger courseId, Principal principal)
    {
        FacebookHelper f = new FacebookHelper(principal);
        userController.removeSubscription(courseId, f.getId());
        return "redirect:/show-subscribed-course";
    }


    @RequestMapping(value = "/doAddSubstriction/{courseID}")
    public String doAddCourse(@PathVariable(value = "courseID") BigInteger courseId, Principal principal)
    {
        FacebookHelper f = new FacebookHelper(principal);
        userController.addSubscription(courseId, f.getId());
        return "redirect:/find-all-courses";
    }

    @RequestMapping(value = "/deleteSubscription")
    public String deleteSubscription(Principal principal, @RequestParam("id") BigInteger id)
    {
        FacebookHelper f = new FacebookHelper(principal);
        userController.removeSubscription(id, f.getId());
        return "courses";
    }

}
