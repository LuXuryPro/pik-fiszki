package pik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pik.dao.CourseDao;
import pik.dao.UserDao;
import pik.dto.CourseInfo;

import java.util.List;

/**
 * Created by adrian on 07.06.16.
 */

@Controller
public class CourseController {
    // dodaj, edytowac, usunac, pobrac rozne wersje
    private UserDao userDao;
    private CourseDao courseDao;

    @Autowired
    public CourseController(UserDao userDao, CourseDao courseDao) {
        this.userDao = userDao;
        this.courseDao = courseDao;
    }

    public Boolean addCourse(String name, String description, String ownerId) {
        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setName(name);
        courseInfo.setDescription(description);
        courseInfo.setOwnerId(ownerId);
        return courseDao.create(courseInfo) != null;
    }

    public List<CourseInfo> getSubscribedCourses(String userId) {
        return courseDao.getSubscribedCourses(userDao.getById(userId));
    }
}
