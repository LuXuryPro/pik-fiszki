package pik.dao;

import pik.dto.CourseInfo;
import pik.dto.UserInfo;

import java.math.BigInteger;
import java.util.List;

public interface CourseDao {

    CourseInfo create(CourseInfo course);

    CourseInfo update(CourseInfo course);

    Boolean delete(CourseInfo course);

    CourseInfo get(BigInteger id);

    CourseInfo get(String userId, String CourseName);

    List<CourseInfo> getOwnedCourses(String userId);

    List<CourseInfo> getSubscribedCourses(UserInfo user);

    Boolean exists(BigInteger id);

    Boolean exists(String username, String name);

    List<CourseInfo> getAll();

    List<CourseInfo> getUnsubscribed(UserInfo user);
}
