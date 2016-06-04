package pik.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pik.dto.UserInfo;

import static org.junit.Assert.*;

/**
 * Created by Michał on 04.06.2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
public class UserDaoImplTest {

    @Autowired
    UserDao userDao;

    @Test
    public void create() throws Exception {

        UserInfo user = new UserInfo();
        user.setUserId("aaa");

        user = userDao.create(user);
        assertNotEquals(user,null);
    }

    @Test
    public void read() throws Exception {

    }

    @Test
    public void readAll() throws Exception {

    }

    @Test
    public void readAll1() throws Exception {

    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void idExists() throws Exception {

    }

    @Test
    public void userNameExists() throws Exception {

    }

}