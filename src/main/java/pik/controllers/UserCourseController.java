package pik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pik.dao.CourseDao;
import pik.dao.UserDao;

public class UserCourseController
{
    private UserDao userDao;
    private CourseDao courseDao;


    @Autowired
    public UserCourseController(UserDao userDao, CourseDao courseDao)
    {
        this.userDao = userDao;
        this.courseDao = courseDao;
    }


    @RequestMapping("/show-course")
    public String showCourses(Model model)
    {
        CourseController courseController = new CourseController(userDao, courseDao);
        model.addAttribute("courseController", courseController);
        return "courses";
    }
}
