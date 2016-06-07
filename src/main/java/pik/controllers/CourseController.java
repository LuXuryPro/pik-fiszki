package pik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pik.dao.CourseDao;
import pik.dao.UserDao;
import pik.dto.CourseInfo;
import pik.exceptions.CourseAccessException;
import pik.repositories.CourseRepository;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by adrian on 07.06.16.
 */

@Component
public class CourseController {
    private UserDao userDao;
    private CourseDao courseDao;

    @Autowired
    public CourseController(UserDao userDao, CourseDao courseDao, CourseRepository courseRepository) {
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

    public Boolean editCourse(CourseInfo courseInfo, String userId) throws CourseAccessException{
        if (courseDao.exists(courseInfo.getId()))
            return false;
        if (!courseInfo.getOwnerId().equals(userId))
            throw new CourseAccessException("Access denided", courseInfo.getId(), userId);
        return courseDao.update(courseInfo) != null;
    }

    public Boolean deleteCourse(BigInteger courseId, String userId) throws CourseAccessException {
        CourseInfo courseInfo = courseDao.get(courseId);
        if (!courseInfo.getOwnerId().equals(userId))
            throw new CourseAccessException("Access denided", courseId, userId);
        return courseDao.delete(courseInfo) != null;
    }

    public List<CourseInfo> getSubscribedCourses(String userId) {
        return courseDao.getSubscribedCourses(userDao.getById(userId));
    }
}
