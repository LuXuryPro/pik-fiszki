package pik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pik.dao.CourseDao;
import pik.dao.QuestionDao;
import pik.dao.UserDao;
import pik.dto.QuestionInfo;
import pik.dto.UserInfo;
import pik.exceptions.CourseAccessException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class QuestionsController {
    private UserDao userDao;
    private CourseDao courseDao;
    private QuestionDao questionDao;

    @Autowired
    QuestionsController(UserDao userDao, CourseDao courseDao, QuestionDao questionDao) {
        this.userDao = userDao;
        this.courseDao = courseDao;
        this.questionDao = questionDao;
    }

    public Long countAllQuestions(BigInteger courseId) {
        return questionDao.countQuestions(courseId);
    }

    public Integer countActiveQuestions(String userId, BigInteger courseId) {
        return 0;
    }

    public QuestionInfo getFirstQuestion(String userId, BigInteger courseId) {
        return new QuestionInfo();
    }

    public List<QuestionInfo> getAllQuestions(String userId, BigInteger courseId) throws CourseAccessException {
        UserInfo userInfo = userDao.getById(userId);
        if (userInfo.getSubscribedCourses().contains(courseId)) {
            return questionDao.getCourseQuestions(courseId);
        }
        else {
            throw new CourseAccessException("Access forbidden", courseId, userId);
        }
    }

}
