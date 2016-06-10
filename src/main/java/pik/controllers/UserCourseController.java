package pik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pik.Util.FacebookHelper;
import pik.dao.CourseDao;
import pik.dao.UserDao;
import pik.dto.CourseInfo;
import pik.dto.UserInfo;
import pik.repositories.CourseRepository;

import java.math.BigInteger;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserCourseController
{
    private CourseController courseController;
    private QuestionsController questionsController;


    @Autowired
    public UserCourseController(CourseController courseController, QuestionsController questionsController)
    {
        this.courseController = courseController;
        this.questionsController = questionsController;
    }


    @RequestMapping("/show-course")
    public String showCourses(Principal principal, Model model)
    {
        FacebookHelper f = new FacebookHelper(principal);
        List<CourseInfo> courseList = courseController.getUserCourses(f.getId());
        model.addAttribute("courseList", courseList);
        model.addAttribute("courseCount", courseList.size());

        return "courses";
    }

    @RequestMapping("/show-subscribed-course")
    public String showSubscribedCourses(Principal principal, Model model)
    {
        FacebookHelper f = new FacebookHelper(principal);
        List<CourseInfo> courseList = courseController.getSubscribedCourses(f.getId());
        model.addAttribute("courseList", courseList);
        model.addAttribute("courseCount", courseList.size());

        return "subscribedCourses";
    }


    @RequestMapping("/add-course")
    public String addCourse(Model model)
    {
        model.addAttribute("courseController", courseController);
        return "addCourse";
    }


    @RequestMapping("/remove-course")
    public String removeCourse(Model model)
    {
        model.addAttribute("courseController", courseController);
        return "removeCourse";
    }


    @RequestMapping(value = "/saveAddCourse")
    public String doAddCourse(Principal principal, @RequestParam("name") String name, @RequestParam("description") String description)
    {
        FacebookHelper f = new FacebookHelper(principal);
        courseController.addCourse(name, description, f.getId());
        return "redirect:/show-course";
    }

    @RequestMapping(value = "/deleteCourse")
    public String deleteCourse(Principal principal, @RequestParam("courseId") BigInteger id)
    {
        FacebookHelper f = new FacebookHelper(principal);
        try {
            courseController.deleteCourse(id, f.getId());
        }catch (Exception e){}

        return "redirect:/show-course";
    }
}
