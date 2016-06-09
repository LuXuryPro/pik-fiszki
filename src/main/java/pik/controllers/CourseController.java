package pik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pik.dao.CourseDao;
import pik.dao.UserDao;
import pik.dto.CourseInfo;
import pik.exceptions.CourseAccessException;
import pik.repositories.CourseRepository;

import java.math.BigInteger;
import java.util.List;


/**
 * The type Course controller.
 */
@Component
public class CourseController {
    private UserDao userDao;
    private CourseDao courseDao;

    /**
     * Konstruktor kontrolera kursów
     *
     * @param userDao   DAO użytkownika
     * @param courseDao DAO kursów
     */
    @Autowired
    public CourseController(UserDao userDao, CourseDao courseDao) {
        this.userDao = userDao;
        this.courseDao = courseDao;
    }

    /**
     * Dodawanie kursu do bazy danych.
     *
     * @param name        Nazwa kursu
     * @param description Opis kursu
     * @param ownerId     ID użytkownika będącego właścicielem
     * @return Informacja o powodzeniu operacji.
     */
    public Boolean addCourse(String name, String description, String ownerId) {
        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setName(name);
        courseInfo.setDescription(description);
        courseInfo.setOwnerId(ownerId);
        return courseDao.create(courseInfo) != null;
    }

    /**
     * Funkcja edytująca opis kursu.
     *
     * @param courseInfo DTO kursu ze zmienionymi wartościami
     * @param userId     ID użytkownika wykonującego zmianę
     * @return Informacja o powodzeniu
     * @throws CourseAccessException Próba dostępu do nieswojego kursu.
     */
    public Boolean editCourse(CourseInfo courseInfo, String userId) throws CourseAccessException{
        if (courseDao.exists(courseInfo.getId()))
            return false;
        if (!courseInfo.getOwnerId().equals(userId))
            throw new CourseAccessException("Access denided", courseInfo.getId(), userId);
        return courseDao.update(courseInfo) != null;
    }

    /**
     * Dunkcja usuwająca pytanie z bazy danych.
     *
     * @param courseId ID kursu
     * @param userId   ID użytkownika
     * @return Informację o powodzeniu wykonania zadania
     * @throws CourseAccessException Próba dostepu do nieswojego kursu.
     */
    public Boolean deleteCourse(BigInteger courseId, String userId) throws CourseAccessException {
        CourseInfo courseInfo = courseDao.get(courseId);
        if (!courseInfo.getOwnerId().equals(userId))
            throw new CourseAccessException("Access denided", courseId, userId);
        return courseDao.delete(courseInfo);
    }

    /**
     * Pobranie listy kursów, na które zapisany jest użytkownik.
     *
     * @param userId ID użytkownika
     * @return Lista kursów, na które zapisany jest użytkownik
     */
    public List<CourseInfo> getSubscribedCourses(String userId) {
        return courseDao.getSubscribedCourses(userDao.getById(userId));
    }

    /**
     * Pobieranie listy kursów, na które użytkownik nie jest jeszcze zapisany.
     *
     * @param userId ID użytkownika
     * @return Lista kursów.
     */
    public List<CourseInfo> getUnsubscribedCourses(String userId) {
        return courseDao.getUnsubscribed(userDao.getById(userId));
    }

    public String getCourseNameById(BigInteger courseId)
    {
        return courseDao.get(courseId).getName();
    }
}
