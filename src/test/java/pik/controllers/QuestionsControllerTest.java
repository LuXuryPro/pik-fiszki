package pik.controllers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pik.dao.CourseDao;
import pik.dao.QuestionDao;
import pik.dao.UserDao;
import pik.dto.QuestionInfo;
import pik.dto.UserInfo;
import pik.exceptions.CourseAccessException;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class QuestionsControllerTest {
    @Mock
    private UserDao userDao;

    @Mock
    private CourseDao courseDao;

    @Mock
    private QuestionDao questionDao;

    private QuestionsController questionsController;
    private UserInfo userInfoMock;
    private QuestionInfo questionInfoMock;

    @Before
    public void initMock() {
        MockitoAnnotations.initMocks(this);
        questionsController = new QuestionsController(userDao, courseDao, questionDao);

        userInfoMock = new UserInfo("123", "", "", "", "");
        userInfoMock.setSubscribedCourses(Arrays.asList(BigInteger.ONE, BigInteger.TEN));

        questionInfoMock = new QuestionInfo();
        questionInfoMock.setCourseId(BigInteger.ONE);
        questionInfoMock.setId(BigInteger.ZERO);
        questionInfoMock.setAnswer("Ala ma kota");
        questionInfoMock.setQuestion("Co ma Ala?");
    }

    @Test
    public void countAllQuestions() throws Exception {
        Mockito.when(questionDao.countQuestions(BigInteger.ONE)).thenReturn(5L);

        long counter = questionsController.countAllQuestions(BigInteger.ONE);
        Assert.assertEquals(counter, 5L);
    }

    @Test
    public void countActiveQuestions() throws Exception {
        Mockito.when(questionDao.countActiveQuestions(userInfoMock, BigInteger.ONE)).thenReturn(5);
        Mockito.when(userDao.getById("123")).thenReturn(userInfoMock);

        int counter = questionsController.countActiveQuestions("123", BigInteger.ONE);
        Assert.assertEquals(counter, 5);
    }

    @Test(expected = CourseAccessException.class)
    public void countActiveQuestionsError() throws Exception {
        Mockito.when(questionDao.countActiveQuestions(userInfoMock, BigInteger.ZERO)).thenReturn(0);
        Mockito.when(userDao.getById("123")).thenReturn(userInfoMock);

        int counter = questionsController.countActiveQuestions("123", BigInteger.ZERO);
    }

    @Test
    public void getFirstQuestion() throws Exception {

    }

    @Test
    public void getAllQuestions() throws Exception {

        Mockito.when(userDao.getById("123")).thenReturn(userInfoMock);
        Mockito.when(questionDao.getCourseQuestions(BigInteger.ONE)).thenReturn(Arrays.asList(questionInfoMock));

        List<QuestionInfo> questionInfos = questionsController.getAllQuestions("123", BigInteger.ONE);
        QuestionInfo questionInfo = questionInfos.get(0);

        Assert.assertEquals(questionInfo.getQuestion(), questionInfoMock.getQuestion());
        Assert.assertEquals(questionInfo.getAnswer(), questionInfoMock.getAnswer());
        Assert.assertEquals(questionInfo.getCourseId(), questionInfoMock.getCourseId());
        Assert.assertEquals(questionInfo.getId(), questionInfoMock.getId());
    }

    @Test(expected = CourseAccessException.class)
    public void getAllQuestionsError() throws Exception {
        Mockito.when(userDao.getById("123")).thenReturn(userInfoMock);
        Mockito.when(questionDao.getCourseQuestions(BigInteger.ZERO)).thenReturn(Arrays.asList(questionInfoMock));

        List<QuestionInfo> questionInfos = questionsController.getAllQuestions("123", BigInteger.ZERO);

    }

}