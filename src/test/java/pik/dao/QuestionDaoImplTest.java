package pik.dao;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import pik.dto.CourseInfo;
import pik.dto.MarkInfo;
import pik.dto.QuestionInfo;
import pik.dto.UserInfo;
import pik.exceptions.SequenceException;
import pik.repositories.CourseRepository;
import pik.repositories.QuestionRepository;
import pik.repositories.UserRepository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;


@RunWith(PowerMockRunner.class)
public class QuestionDaoImplTest {

    @Mock
    private SequenceDao mockSeqDao;

    @Mock
    private QuestionRepository mockQuestionRep;

    @Mock
    private UserRepository mockUserRep;

    @Mock
    private MongoOperations mockMongoOp;


    @Before
    public void setUp(){
        when(mockQuestionRep.save(any(QuestionInfo.class))).then(returnsFirstArg());
        when(mockUserRep.save(any(UserInfo.class))).then(returnsFirstArg());
        when(mockSeqDao.getNext(anyString())).thenReturn(BigInteger.ONE);
        when(mockQuestionRep.findById(any(BigInteger.class))).thenAnswer(invocation -> {
           QuestionInfo q = new QuestionInfo();
            q.setId((BigInteger) invocation.getArguments()[0]);
            q.setQuestion("test");
            q.setAnswer("test");
            q.setCourseId(BigInteger.ONE);
            return q;
        });

        when(mockMongoOp.find(any(Query.class), any(Class.class))).thenAnswer(invocation -> {
            List<UserInfo> users = new ArrayList<UserInfo>();

            for(int i = 0; i<5 ; ++i){
                UserInfo user =  new UserInfo();
                users.add(user);
            }

            return users;
        });


        when(mockQuestionRep.findAll(any(Iterable.class))).thenAnswer(invocation -> {
            List<QuestionInfo> qlist = new ArrayList<QuestionInfo>();
            for(int i =0; i<10; ++i){
                QuestionInfo info = new QuestionInfo();
                info.setId(BigInteger.valueOf(i));
                qlist.add(info);
            }
            return qlist;
        });
    }

    @Test
    public void add() throws Exception {
        when(mockSeqDao.getNext(anyString())).thenReturn(BigInteger.ONE);

        QuestionDaoImpl dao = new QuestionDaoImpl(mockQuestionRep,mockUserRep,mockSeqDao,mockMongoOp);
        QuestionInfo q = new QuestionInfo();
        q.setCourseId(BigInteger.ONE);
        q.setQuestion("test");
        q.setAnswer("test");

        q = dao.add(q);

        assertNotNull(q);
        assertEquals(q.getAnswer(),"test");
        assertEquals(q.getQuestion(),"test");
        assertEquals(q.getCourseId(),BigInteger.ONE);


    }

    @Test
    public void addWithNoSeq() throws Exception{
        when(mockSeqDao.getNext(anyString())).thenThrow(SequenceException.class).thenReturn(BigInteger.ONE);

        QuestionDaoImpl dao = new QuestionDaoImpl(mockQuestionRep,mockUserRep,mockSeqDao,mockMongoOp);
        QuestionInfo q = new QuestionInfo();
        q.setCourseId(BigInteger.ONE);
        q.setQuestion("test");
        q.setAnswer("test");

        q = dao.add(q);

        assertNotNull(q);
        assertEquals(q.getAnswer(),"test");
        assertEquals(q.getQuestion(),"test");
        assertEquals(q.getCourseId(),BigInteger.ONE);
    }

    @Test
    public void update() throws Exception {
        QuestionDaoImpl dao = new QuestionDaoImpl(mockQuestionRep,mockUserRep,mockSeqDao,mockMongoOp);
        QuestionInfo q = new QuestionInfo();
        q.setCourseId(BigInteger.ONE);
        q.setQuestion("test");
        q.setAnswer("test");

        q = dao.update(q);

        assertNotNull(q);
        assertEquals(q.getAnswer(),"test");
        assertEquals(q.getQuestion(),"test");
        assertEquals(q.getCourseId(),BigInteger.ONE);
    }

    @Test
    public void remove() throws Exception {
        QuestionDaoImpl dao = new QuestionDaoImpl(mockQuestionRep,mockUserRep,mockSeqDao,mockMongoOp);
        QuestionInfo q = new QuestionInfo();
        q.setCourseId(BigInteger.ONE);
        q.setQuestion("test");
        q.setAnswer("test");

        Assert.assertTrue(dao.remove(q));
    }

    @Test
    public void getQuestionToAnswer() throws Exception {
        QuestionDaoImpl dao = new QuestionDaoImpl(mockQuestionRep,mockUserRep,mockSeqDao,mockMongoOp);

        UserInfo info = new UserInfo();
        List<MarkInfo> marks = prepareMarks();

        info.setMarks(marks);


        CourseInfo course = new CourseInfo();
        course.setId(BigInteger.ONE);

        Page<QuestionInfo> page = dao.getQuestionToAnswer(info,course,new PageRequest(0,10));

        Assert.assertNotNull(page);
        List<QuestionInfo> list = page.getContent();
        assertEquals(10,list.size());
    }


    @Test
    public void countActiveQuestions() throws Exception {
        QuestionDaoImpl dao = new QuestionDaoImpl(mockQuestionRep,mockUserRep,mockSeqDao,mockMongoOp);

        UserInfo info = new UserInfo();
        List<MarkInfo> marks = prepareMarks();
        info.setMarks(marks);
        CourseInfo course = new CourseInfo();
        course.setId(BigInteger.ONE);
        int count = dao.countActiveQuestions(info,BigInteger.ONE);
        assertEquals(10,count);
    }

    private List<MarkInfo> prepareMarks(){
        List<MarkInfo> marks = new ArrayList<MarkInfo>();

        for (int i = 0; i<10; ++i){
            MarkInfo mark = new MarkInfo();
            mark.setCourseId(BigInteger.ONE);
            mark.setDate(new Date());
            mark.setQuestionId(BigInteger.valueOf(i));
            marks.add(mark);
        }

        return marks;
    }

}