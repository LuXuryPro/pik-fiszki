package pik.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.data.mongodb.core.MongoOperations;
import pik.dto.CourseInfo;
import pik.dto.QuestionInfo;
import pik.dto.UserInfo;
import pik.exceptions.SequenceException;
import pik.repositories.CourseRepository;
import pik.repositories.MarkRepository;
import pik.repositories.QuestionRepository;
import pik.repositories.UserRepository;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import org.mockito.Mock;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

/**
 * Created by Michał on 06.06.2016.
 */
@RunWith(PowerMockRunner.class)
//@PrepareForTest({FacebookTemplate.class, IndexController.class})
public class CourseDaoImplTest {

    @Mock
    private SequenceDao mockSeqDao;

    @Mock
    private CourseRepository mockCourseRep;

    @Mock
    private QuestionRepository mockQuestionRep;

    @Mock
    private MarkRepository mockMarkRep;

    @Mock
    private UserRepository mockUserRep;

    @Mock
    private MongoOperations mockMongoOp;


    @Before
    public void setUp(){
        when(mockCourseRep.save(any(CourseInfo.class))).then(returnsFirstArg());

        when(mockCourseRep.findById(any(BigInteger.class))).thenAnswer(invocation -> {
            CourseInfo info = new CourseInfo();
            info.setId((BigInteger) invocation.getArguments()[0]);
            info.setOwnerId("owner");
            info.setDescription("course");
            info.setName("name");
            return info;
        });

    }

    @Test
    public void create() throws Exception {
        when(mockSeqDao.getNext(anyString())).thenReturn(BigInteger.ONE);
        when(mockCourseRep.save(any(CourseInfo.class))).then(returnsFirstArg());

        CourseDaoImpl dao = new CourseDaoImpl(mockSeqDao,mockCourseRep,mockQuestionRep,
                mockMarkRep,mockUserRep,mockMongoOp);

        CourseInfo course = new CourseInfo();
        course.setName("test");
        course.setDescription("test");

        CourseInfo res = dao.create(course);

        assertEquals("test", res.getName());
        assertEquals("test", res.getDescription());
        assertEquals(BigInteger.ONE,res.getId());

    }

    @Test
    public void createWithNoSeq() throws Exception {
        when(mockSeqDao.getNext(anyString())).thenThrow(SequenceException.class).thenReturn(BigInteger.ONE);

        CourseDaoImpl dao = new CourseDaoImpl(mockSeqDao,mockCourseRep,mockQuestionRep,
                mockMarkRep,mockUserRep,mockMongoOp);

        CourseInfo course = new CourseInfo();
        course.setName("test");
        course.setDescription("test");

        CourseInfo res = dao.create(course);

        assertNotNull(res);
        assertEquals("test", res.getName());
        assertEquals("test", res.getDescription());
        assertEquals(BigInteger.ONE,res.getId());

    }


    @Test
    public void update() throws Exception {

        CourseDaoImpl dao = new CourseDaoImpl(mockSeqDao,mockCourseRep,mockQuestionRep,
                mockMarkRep,mockUserRep,mockMongoOp);

        CourseInfo course = new CourseInfo();
        course.setId(BigInteger.ONE);
        course.setName("test");
        course.setDescription("test");

        CourseInfo res = dao.update(course);

        assertNotNull(res);
        assertEquals("test",res.getDescription());
        assertEquals("test",res.getName());
        assertEquals(BigInteger.ONE,res.getId());
    }

    @Test
    public void delete() throws Exception {
        when(mockQuestionRep.findByCourseId(any(BigInteger.class))).thenAnswer(invocation -> {
            QuestionInfo info = new QuestionInfo();
            info.setId((BigInteger) invocation.getArguments()[0]);
            info.setAnswer("test");
            info.setQuestion("test");
            info.setCourseId(BigInteger.ONE);
            List<QuestionInfo> list = new ArrayList<QuestionInfo>();
            list.add(info);
            return list;
        });

        CourseDaoImpl dao = new CourseDaoImpl(mockSeqDao,mockCourseRep,mockQuestionRep,
                mockMarkRep,mockUserRep,mockMongoOp);
        CourseInfo course = new CourseInfo();
        course.setId(BigInteger.ONE);
        course.setName("test");
        course.setDescription("test");

        Assert.assertTrue(dao.delete(course));

    }


    @Test
    public void getSubscribedCourses() throws Exception {

        when(mockCourseRep.findAll(Matchers.anyListOf(BigInteger.class))).thenAnswer(invocation -> {
            List<CourseInfo> list = new ArrayList<CourseInfo>();
            for(BigInteger id: (List<BigInteger>)invocation.getArguments()[0]){
                CourseInfo info = new CourseInfo();
                info.setId(id);
                list.add(info);
            }
            list.remove(((List<BigInteger>)invocation.getArguments()[0]).get(0));

            return list;
        });


        UserInfo user = new UserInfo("id","fname","lname","mail","user");

        List<BigInteger> list = new ArrayList<BigInteger>();
        for(BigInteger i = BigInteger.ONE; i.compareTo(BigInteger.TEN) == -1; i = i.add(BigInteger.ONE)){
            list.add(i);
        }

        user.setSubscribedCourses(list);

        CourseDaoImpl dao = new CourseDaoImpl(mockSeqDao,mockCourseRep,mockQuestionRep,
                mockMarkRep,mockUserRep,mockMongoOp);

        List<CourseInfo> courses = dao.getSubscribedCourses(user);

        assertNotNull(courses);
        assertEquals(9,courses.size());
    }

}