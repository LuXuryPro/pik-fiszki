package pik.controllers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pik.dao.CourseDao;
import pik.dao.UserDao;
import pik.dto.CourseInfo;
import pik.dto.UserInfo;
import pik.exceptions.CourseAccessException;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

public class CourseControllerTest {
    @Mock
    private UserDao userDao;

    @Mock
    private CourseDao courseDao;

    private CourseController courseController;

    @Before
    public void initMock() {
        MockitoAnnotations.initMocks(this);
        courseController = new CourseController(userDao, courseDao);
    }

    @Test
    public void addCourse() throws Exception {
        Mockito.when(courseDao.create(any(CourseInfo.class))).then(returnsFirstArg());
        Assert.assertTrue(courseController.addCourse("Ala", "Makota", "Adrian"));
    }

    @Test
    public void editCourse() throws Exception {
        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setOwnerId("ABC");
        Mockito.when(courseDao.exists(BigInteger.ZERO)).thenReturn(false);
        Mockito.when(courseDao.exists(BigInteger.ONE)).thenReturn(true);
        Mockito.when(courseDao.update(any(CourseInfo.class))).then(returnsFirstArg());

        courseInfo.setId(BigInteger.ZERO);
        Assert.assertTrue(courseController.editCourse(courseInfo, "ABC"));
        courseInfo.setId(BigInteger.ONE);
        Assert.assertFalse(courseController.editCourse(courseInfo, "ABC"));
    }

    @Test
    public void deleteCourse() throws Exception {
        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setOwnerId("ABC");
        Mockito.when(courseDao.get(any(BigInteger.class))).thenReturn(courseInfo);
        Mockito.when(courseDao.delete(any(CourseInfo.class))).thenReturn(true);
        Assert.assertTrue(courseController.deleteCourse(BigInteger.ONE, "ABC"));
    }

    @Test
    public void getCourses() throws Exception {
        CourseInfo c1 = new CourseInfo();
        CourseInfo c2 = new CourseInfo();
        List<CourseInfo> list = Arrays.asList(c1, c2);
        Mockito.when(courseDao.getSubscribedCourses(any(UserInfo.class))).thenReturn(list);
        Mockito.when(userDao.getById(anyString())).thenReturn(new UserInfo());
        Mockito.when(courseDao.getUnsubscribed(any(UserInfo.class))).thenReturn(list);

        Assert.assertEquals(list, courseController.getSubscribedCourses("ABC"));
        Assert.assertEquals(list, courseController.getUnsubscribedCourses("CDE"));
    }


}