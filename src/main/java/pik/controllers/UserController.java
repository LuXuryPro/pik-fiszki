package pik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import pik.dao.UserDao;
import pik.dto.UserInfo;

import java.math.BigInteger;

@Component
public class UserController {
    private UserDao userDao;

    /**
     * Tworzy nowy kontroler użytkowników - warstwa abstrakcji dla front-endu.
     *
     * @param userDao Automatycznie wstrzykiwany obiekt User DAO.
     */
    @Autowired
    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Funkcja dodająca nowego użytkownika do bazy danych.
     *
     * @param id        ID użytkownika pochodzące z serwisu Facebook
     * @param firstName Imię użytkownika z serwisu Facebook
     * @param lastName  Nazwisko użytkownika z serwisu Facebook
     * @param email     E-mail z serwisu Facebook
     * @return Informacja, czy udało się dodać użytkownika.
     */
    public Boolean addUser(String id, String firstName, String lastName, String email) {
        if (userDao.idExists(id)) {
            return false;
        }
        UserInfo userInfo = new UserInfo(id, firstName, lastName, email);
        return userDao.create(userInfo) != null;
    }

    /**
     * Funkcja pobierająca użytkownika z bazy danych.
     *
     * @param userId ID użytkownika z serwisu Facebook
     * @return DTO zawierjaące informacje o użytkowniku.
     */
    public UserInfo getUser(String userId) {
        return userDao.getById(userId);
    }

    /**
     * Funkcja edytująca dane użytkownika.
     *
     * @param userInfo DTO ze zmienionymi danymi użytkownika.
     * @return Informacja o powodzeniu operacji.
     */
    public Boolean editUser(UserInfo userInfo) {
        return userDao.update(userInfo) != null;
    }

    /**
     * Funkcja zapisująca użytkownika na kurs.
     *
     * @param courseId ID kursu
     * @param userId   ID użytkownika z serwisu Facebook
     * @return Informacja o powodzeniu operacji.
     */
    public Boolean addSubscription(BigInteger courseId, String userId) {
        UserInfo userInfo = userDao.getById(userId);
        return userDao.subscribe(userInfo, courseId);
    }

    /**
     * Funkcja usuwająca kurs z subskrypcji użytkownika.
     *
     * @param courseId ID kursu
     * @param userId   ID użytkownika z serwisu Facebook
     * @return Informacja o powodzeniu operacji.
     */
    public Boolean removeSubscription(BigInteger courseId, String userId) {
        UserInfo userInfo = userDao.getById(userId);
        return userDao.unsubscribe(userInfo, courseId);
    }
}
