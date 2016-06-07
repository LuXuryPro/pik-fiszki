package pik.dao;

import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import pik.dto.*;
import pik.exceptions.SequenceException;
import pik.repositories.CourseRepository;
import pik.repositories.QuestionRepository;
import pik.repositories.UserRepository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import pik.dto.UserInfo;

/**
 * Created by Michał on 04.06.2016.
 */
@Service
@ComponentScan(basePackages = {"repositories"})
public class QuestionDaoImpl implements QuestionDao {

    private QuestionRepository questionRepository;

    private UserRepository userRepository;

    private SequenceDao seqDao;

    private MongoOperations mongoOperations;

    @Autowired
    public QuestionDaoImpl(QuestionRepository questionRepository, UserRepository userRepository,
                           SequenceDao sequenceDao, MongoOperations mongoOperations) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.seqDao = sequenceDao;
        this.mongoOperations = mongoOperations;
    }

    private static final String QUEST_SEQ_KEY = "question";

    public QuestionInfo add(QuestionInfo questionInfo){

        BigInteger courseId = questionInfo.getCourseId();

        try {
            questionInfo.setId(seqDao.getNext(QUEST_SEQ_KEY));
        }
        catch(SequenceException seq){
            seqDao.insert(QUEST_SEQ_KEY);
            questionInfo.setId(seqDao.getNext(QUEST_SEQ_KEY));
        }

        Query query = new Query();
        query.addCriteria(Criteria.where("subscribedcourses").in(courseId));
        List<UserInfo> users = mongoOperations.find(query, UserInfo.class);

        for(UserInfo user: users){
            MarkInfo mark = new MarkInfo();
            mark.setCounter(0);
            mark.setCourseId(courseId);
            mark.setQuestionId(questionInfo.getId());
            mark.setDate(new Date());
            mark.setEf(2.5f);
            mark.setInterval(0);

            user.getMarks().add(mark);
        }

        userRepository.save(users);
        return questionRepository.save(questionInfo);
    }

    public QuestionInfo update(QuestionInfo questionInfo) {

        BigInteger questionId = questionInfo.getId();
        BigInteger courseId  = questionInfo.getCourseId();
        Query query = new Query(new Criteria().andOperator(
                Criteria.where("subscribedcourses").in(courseId),
                Criteria.where("marks").elemMatch(Criteria.where("questionId").is(questionId))
        ));
        Update update = new Update().set("marks.$.ef", 2.5f).set("counter",0).set("date", new Date()).set("interval", 0);
        mongoOperations.updateMulti(query,update,"user");

        return questionRepository.save(questionInfo);
    }

    public Boolean remove(QuestionInfo questionInfo) {
        QuestionInfo existingQuest = questionRepository.findById(questionInfo.getId());

        if (existingQuest == null) {
            return false;
        }
        BigInteger questId = existingQuest.getId();

        mongoOperations.updateMulti(new Query(),
                new Update().pull("marks", Query.query(Criteria.where("questionId").is(questId))), "user");


        questionRepository.delete(existingQuest);
        return true;
    }

    public List<QuestionInfo> getCourseQuestions(BigInteger courseId) {
        return questionRepository.findByCourseId(courseId);
    }

    public Page<QuestionInfo> getQuestionToAnswer(UserInfo user, CourseInfo course, Pageable page) {
        Date today = new Date();
        BigInteger courseId = course.getId();
        List<MarkInfo> marks = user.getMarks();

        List<BigInteger> ids = new ArrayList<BigInteger>();

        for (MarkInfo mark: marks){

            if(mark.getCourseId() ==courseId && mark.getDate().before(today))
                ids.add(mark.getQuestionId());
        }
        Iterable<QuestionInfo> questIter = questionRepository.findAll(ids);

        List<QuestionInfo> quests = makeCollection(questIter);

        int pageNo = page.getPageNumber();
        int pageSize = page.getPageSize();

        int startIdx = pageNo * pageSize;
        int endIdx = startIdx + pageSize;

        List<QuestionInfo> sub = quests.subList(startIdx,endIdx);

        return new PageImpl<QuestionInfo>(sub);
    }

    private static List<QuestionInfo> makeCollection(Iterable<QuestionInfo> iter) {
        List<QuestionInfo> list = new ArrayList<QuestionInfo>();
        for (QuestionInfo item : iter) {
            list.add(item);
        }
        return list;
    }

    public Long countQuestions(BigInteger courseId) {
        return questionRepository.countByCourseId(courseId);
    }

    public int countActiveQuestions(UserInfo user, BigInteger courseId) {
        Date today = new Date();

        List<BigInteger> ids = new ArrayList<BigInteger>();

        for (MarkInfo mark: user.getMarks()){

            if(mark.getCourseId().equals(courseId) && mark.getDate().before(today))
                ids.add(mark.getQuestionId());
        }
        Iterable<QuestionInfo> questIter = questionRepository.findAll(ids);

        List<QuestionInfo> quests = makeCollection(questIter);

        return quests.size();
    }
}
