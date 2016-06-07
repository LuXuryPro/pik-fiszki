package pik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pik.dao.CourseDao;
import pik.dao.UserDao;


@Controller
public class OthersCoursesController
{
    private UserDao userDao;
    private CourseDao courseDao;


    @Autowired
    public OthersCoursesController(UserDao userDao, CourseDao courseDao)
    {
        this.userDao = userDao;
        this.courseDao = courseDao;
    }


    @RequestMapping("/find-all-courses")
    public String showCourses(Model model)
    {
        CourseController courseController = new CourseController(userDao, courseDao);
        // List<CourseInfo> courses = courseController.getSubscribedCourses(userDao.);
        model.addAttribute("courseController", courseController);
        return "allCourses";
    }


    @RequestMapping("/add-sub")
    public String addCourse(Model model)
    {
        CourseController courseController = new CourseController(userDao, courseDao);
        model.addAttribute("courseController", courseController);
        return "addSubscription";
    }


    @RequestMapping("/remove-sub")
    public String removeCourse(Model model)
    {
        CourseController courseController = new CourseController(userDao, courseDao);
        model.addAttribute("courseController", courseController);
        return "removeSubscription";
    }
}