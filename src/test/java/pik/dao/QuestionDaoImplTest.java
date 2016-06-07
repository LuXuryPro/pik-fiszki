package pik.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import pik.dto.CourseInfo;
import pik.dto.QuestionInfo;
import pik.dto.UserInfo;
import pik.exceptions.SequenceException;
import pik.repositories.CourseRepository;
import pik.repositories.QuestionRepository;
import pik.repositories.UserRepository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by Michał on 07.06.2016.
 */
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
        /*
        BigInteger questionId = questionInfo.getId();
        BigInteger courseId  = questionInfo.getCourseId();
        Query query = new Query(new Criteria().andOperator(
                Criteria.where("subscribedcourses").in(courseId),
                Criteria.where("marks").elemMatch(Criteria.where("questionId").is(questionId))
        ));
        Update update = new Update().set("marks.$.ef", 2.5f).set("counter",0).set("date", new Date()).set("interval", 0);
        mongoOperations.updateMulti(query,update,"user");

        return questionRepository.save(questionInfo);*/
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
    public void getCourseQuestions() throws Exception {

    }

    @Test
    public void getQuestionToAnswer() throws Exception {

    }

    @Test
    public void countQuestions() throws Exception {

    }

    @Test
    public void countActiveQuestions() throws Exception {

    }

}