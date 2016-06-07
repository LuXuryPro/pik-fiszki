package pik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import pik.dao.CourseDao;
import pik.dao.QuestionDao;
import pik.dao.UserDao;
import pik.dto.CourseInfo;
import pik.dto.MarkInfo;
import pik.dto.QuestionInfo;
import pik.dto.UserInfo;
import pik.exceptions.CourseAccessException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.lang.Math.*;

@Component
public class QuestionsController {
    private UserDao userDao;
    private CourseDao courseDao;
    private QuestionDao questionDao;

    /**
     * Konstruktor kontrolera pytań
     *
     * @param userDao     DAO użytkowników
     * @param courseDao   DAO kursów
     * @param questionDao DAO pytań
     */
    @Autowired
    QuestionsController(UserDao userDao, CourseDao courseDao, QuestionDao questionDao) {
        this.userDao = userDao;
        this.courseDao = courseDao;
        this.questionDao = questionDao;
    }

    /**
     * Funkcja zwracająca liczbę pytań dla danego kursu
     *
     * @param courseId ID kursu
     * @return Liczba pytań w kursie.
     */
    public Long countAllQuestions(BigInteger courseId) {
        return questionDao.countQuestions(courseId);
    }

    /**
     * Funkcja zliczająca liczbę pytań w danym kursie, które oczekują na użytkownika.
     *
     * @param userId   ID użytkownika
     * @param courseId ID kursu
     * @return Liczba gotowych pytań.
     * @throws CourseAccessException Próba dostępu do nieuprawnionego kursu.
     */
    public Integer countActiveQuestions(String userId, BigInteger courseId) throws CourseAccessException {
        UserInfo userInfo = userDao.getById(userId);
        if (userInfo.getSubscribedCourses().contains(courseId)) {
            return questionDao.countActiveQuestions(userInfo, courseId);
        }
        else {
            throw new CourseAccessException("You are not subscribed to this course", courseId, userId);
        }
    }

    /**
     * Pobierz pierwsze pytanie do wyświetlenia
     *
     * @param userId   ID użytkownika
     * @param courseId ID kursów
     * @return Pierwsze pytanie w kolejce
     * @throws CourseAccessException Próba dostępu do kursu, na który użytkownik nie jest zapisany.
     */
    public QuestionInfo getFirstQuestion(String userId, BigInteger courseId) throws CourseAccessException {
        UserInfo userInfo = userDao.getById(userId);
        CourseInfo courseInfo = courseDao.get(courseId);
        Pageable page = new PageRequest(0,1);
        if (userInfo.getSubscribedCourses().contains(courseId)) {
            return questionDao.getQuestionToAnswer(userInfo, courseInfo, page).getContent().get(0);
        }
        else {
            throw new CourseAccessException("You are not subscribed to this course.", courseId, userId);
        }
    }

    /**
     * Pobierz wszystkie pytania.
     *
     * @param userId   ID usera
     * @param courseId ID kursu
     * @return Lista pytań
     * @throws CourseAccessException Próba dostępu do kursu, na który dana osoba nie jest zapisana.
     */
    public List<QuestionInfo> getAllQuestions(String userId, BigInteger courseId) throws CourseAccessException {
        UserInfo userInfo = userDao.getById(userId);
        if (userInfo.getSubscribedCourses().contains(courseId)) {
            return questionDao.getCourseQuestions(courseId);
        }
        else {
            throw new CourseAccessException("Access forbidden", courseId, userId);
        }
    }

    /**
     * Funkcja dodająca pytanie do bazy danych w danym kursie.
     *
     * @param answer   pytanie
     * @param question odpowiedź
     * @param courseId ID kursu
     * @param userId   ID użytkownika
     * @return Informacja o powodzeniu zadania
     * @throws CourseAccessException Próba dostępu do kursu, którego nie jest się właścicielem.
     */
    public Boolean addQuestion(String answer, String question, BigInteger courseId, String userId)
            throws CourseAccessException {
        CourseInfo courseInfo = courseDao.get(courseId);
        if (!courseInfo.getOwnerId().equals(userId))
            throw new CourseAccessException("You are not owner of this course", courseId, userId);

        QuestionInfo questionInfo = new QuestionInfo();
        questionInfo.setCourseId(courseId);
        questionInfo.setAnswer(answer);
        questionInfo.setQuestion(question);

        return questionDao.add(questionInfo) != null;
    }

    /**
     * Edycja pytania w bazie danych
     *
     * @param questionInfo DTO z pytaniem
     * @param userId       ID użytkownika
     * @return Informacja o powodzeniu operacji.
     * @throws CourseAccessException Informajca o próbie dostępu do nieswojego kursu.
     */
    public Boolean editQuestion(QuestionInfo questionInfo, String userId)
            throws CourseAccessException {
        BigInteger courseId = questionInfo.getCourseId();
        CourseInfo courseInfo = courseDao.get(courseId);
        if (!courseInfo.getOwnerId().equals(userId))
            throw new CourseAccessException("You are not owner of this course", courseId, userId);

        return questionDao.update(questionInfo) != null;
    }

    /**
     * Usunięcie pytania z kursu.
     *
     * @param questionInfo DTO z pytaniem do usunięcia.
     * @param userId       ID użytkownika
     * @return Informacja o powodzeniu operacji.
     * @throws CourseAccessException Próba dostępu do nieswojego kursu.
     */
    public Boolean deleteQuestion(QuestionInfo questionInfo, String userId) throws CourseAccessException {
        BigInteger courseId = questionInfo.getCourseId();
        CourseInfo courseInfo = courseDao.get(courseId);
        if (!courseInfo.getOwnerId().equals(userId))
            throw new CourseAccessException("You are not owner of this course", courseId, userId);

        return questionDao.remove(questionInfo);
    }

    /**
     * Funkcja służąca do ocenienia pytania.
     *
     * @param questionInfo DTO z pytaniem (wystaczy podanie id pytania oraz kursu)
     * @param userId       ID użytkownika
     * @param score        ocena
     * @return Informacja o powodzeniu operacji.
     */
    public Boolean markQuestion(QuestionInfo questionInfo, String userId, int score) {
        UserInfo userInfo = userDao.getById(userId);
        List<MarkInfo> markInfoList = userInfo.getMarks();
        BigInteger questionId = questionInfo.getId();
        BigInteger courseId = questionInfo.getCourseId();
        for (MarkInfo markInfo : markInfoList) {
            if (markInfo.getCourseId().equals(courseId) && markInfo.getQuestionId().equals(questionId)) {
                markAlgorithm(markInfo, score);
                break;
            }
        }
        return userDao.update(userInfo) != null;
    }

    /**
     * Funkcja wyliczająca nowe wartości parametrów algorytmu SuperMemo.
     *
     * @param markInfoOld DTO z paramaterami przed ocenami. Nastawia wartości na nowe.
     * @param score Ocena
     */
    private void markAlgorithm(MarkInfo markInfoOld, int score) {
        markInfoOld.setCounter(markInfoOld.getCounter() + 1);
        int counter = markInfoOld.getCounter();
        float newEf = Math.max(1.3f, markInfoOld.getEf() - 0.8f + 0.28f*score - 0.02f*score*score);
        markInfoOld.setEf(newEf);
        long add_to_date = 1000 * 60 * 60 * 24;
        if (counter == 2)
            add_to_date *= 6;
        else if (counter > 2)
            add_to_date += markInfoOld.getInterval();
        markInfoOld.setInterval(add_to_date);
        Date date = new Date(markInfoOld.getDate().getTime() + add_to_date);
        markInfoOld.setDate(date);
    }
}
