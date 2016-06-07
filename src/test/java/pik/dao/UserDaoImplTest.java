package pik.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.data.mongodb.core.MongoOperations;
import pik.dto.CourseInfo;
import pik.dto.MarkInfo;
import pik.dto.QuestionInfo;
import pik.dto.UserInfo;
import pik.repositories.CourseRepository;
import pik.repositories.MarkRepository;
import pik.repositories.QuestionRepository;
import pik.repositories.UserRepository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;


@RunWith(PowerMockRunner.class)
public class UserDaoImplTest {

    @Mock
    private QuestionRepository mockQuestionRep;

    @Mock
    private UserRepository mockUserRep;

    @Mock
    private MarkRepository mockMarkrep;

    @Mock
    private CourseRepository mockCourseRep;

    private UserDaoImpl dao;


    @Before
    public void setUp(){
        when(mockUserRep.findByUserId(anyString())).thenAnswer(invocation -> {
            UserInfo user = new UserInfo();
            user.setUserId((String) invocation.getArguments()[0]);
            MarkInfo mark = new MarkInfo();
            mark.setCourseId(BigInteger.ONE);
            mark.setQuestionId(BigInteger.ONE);

            List<MarkInfo> marks = user.getMarks();
            marks.add(mark);
            user.setMarks(marks);

            return user;
        });

        when(mockCourseRep.findById(any(BigInteger.class))).thenAnswer(invocation -> {
            CourseInfo course = new CourseInfo();
            course.setId((BigInteger) invocation.getArguments()[0]);
            return course;
        });

        when(mockQuestionRep.findByCourseId(any(BigInteger.class))).thenAnswer(invocation -> {
            List<QuestionInfo> qs = new ArrayList<QuestionInfo>();
            for(int i = 0; i<10;++i){
                QuestionInfo q = new QuestionInfo();
                q.setId(BigInteger.valueOf(i));
                q.setQuestion("test");
                q.setAnswer("test");
                q.setCourseId((BigInteger) invocation.getArguments()[0]);
                qs.add(q);
            }

            return qs;

        });


        when(mockUserRep.save(any(UserInfo.class))).then(returnsFirstArg());

    }


    @Test
    public void update() throws Exception {
        UserInfo user = new UserInfo();
        user.setUserId("Login");
        dao = new UserDaoImpl(mockUserRep,mockMarkrep,mockCourseRep,mockQuestionRep);
        user = dao.update(user);
        assertEquals("Login",user.getUserId());
    }

    @Test
    public void subscribeSucces() throws Exception {
        UserInfo user = new UserInfo();
        List<BigInteger> courses = user.getSubscribedCourses();
        courses.add(BigInteger.ONE);

        dao = new UserDaoImpl(mockUserRep,mockMarkrep,mockCourseRep,mockQuestionRep);

        Assert.assertTrue(dao.subscribe(user,BigInteger.TEN));
        Assert.assertFalse(dao.subscribe(user,BigInteger.ONE));
    }

    @Test
    public void unsubscribeSuccess() throws Exception {
        UserInfo user = new UserInfo();
        List<BigInteger> courses = user.getSubscribedCourses();
        courses.add(BigInteger.ONE);

        dao = new UserDaoImpl(mockUserRep,mockMarkrep,mockCourseRep,mockQuestionRep);

        Assert.assertTrue(dao.unsubscribe(user,BigInteger.ONE));
        Assert.assertFalse(dao.unsubscribe(user,BigInteger.TEN));
    }

}