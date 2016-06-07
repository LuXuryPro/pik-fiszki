package pik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pik.dao.UserDao;
import pik.dto.UserInfo;

/**
 * Created by adrian on 07.06.16.
 */

@Controller
public class UserController {
    private UserDao userDao;

    @Autowired
    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    public Boolean addUser(String id, String firstName, String lastName, String email) {
        if (userDao.idExists(id)) {
            return false;
        }
        UserInfo userInfo = new UserInfo(id, firstName, lastName, email);
        return userDao.create(userInfo) != null;
    }

    public UserInfo getUser(String userId) {
        return userDao.getById(userId);
    }

    public Boolean editUser(UserInfo userInfo) {
        // Tu mozna dodac walidacje
        return userDao.update(userInfo) != null;
    }
}
