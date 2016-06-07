package pik.controllers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pik.dao.UserDao;
import pik.dto.UserInfo;

import java.math.BigInteger;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;

public class UserControllerTest {


    @Mock
    private UserDao userDao;
    private UserController userController;

    @Before
    public void initMock() throws Exception {
        MockitoAnnotations.initMocks(this);
        userController = new UserController(userDao);
    }

    @Test
    public void testAddUserOk() throws Exception {
        Mockito.when(userDao.idExists(any(String.class))).thenReturn(false);
        Mockito.when(userDao.create(any(UserInfo.class))).then(returnsFirstArg());

        Assert.assertTrue(userController.addUser("123", "Ala", "Makota", "abc@ghj.pl"));
    }

    @Test
    public void testAddUserFail() throws Exception {
        Mockito.when(userDao.idExists(any(String.class))).thenReturn(true);
        Mockito.when(userDao.create(any(UserInfo.class))).then(returnsFirstArg());

        Assert.assertFalse(userController.addUser("123", "Ala", "Makota", "abc@gfh.pl"));
    }

    @Test
    public void testGetUser() throws Exception {
        UserInfo userInfo = new UserInfo("123", "Ala", "Makota", "abc@dcf.pl");
        Mockito.when(userDao.getById("123")).thenReturn(userInfo);

        UserInfo getUser = userController.getUser("123");
        Assert.assertEquals(getUser.getFirstName(), userInfo.getFirstName());
        Assert.assertEquals(getUser.getLastName(), userInfo.getLastName());
        Assert.assertEquals(getUser.getEmail(), userInfo.getEmail());
    }

    @Test
    public void testEditUser() throws Exception {
        UserInfo userInfo = new UserInfo("123", "Ala", "Makota", "abc@dcf.pl");
        Mockito.when(userDao.update(any(UserInfo.class))).then(returnsFirstArg());

        Assert.assertTrue(userController.editUser(userInfo));
    }

    @Test
    public void testAddSubscription() throws Exception {
        UserInfo userInfo = new UserInfo("123", "Ala", "Makota", "abc@dcf.pl");
        Mockito.when(userDao.getById(any(String.class))).thenReturn(userInfo);
        Mockito.when(userDao.subscribe(any(UserInfo.class), any(BigInteger.class))).thenReturn(true);

        Assert.assertTrue(userController.addSubscription(BigInteger.ONE, "123"));
    }

    @Test
    public void testRemoveSubscription() throws Exception {
        UserInfo userInfo = new UserInfo("123", "Ala", "Makota", "abc@dcf.pl");
        Mockito.when(userDao.getById(any(String.class))).thenReturn(userInfo);
        Mockito.when(userDao.unsubscribe(any(UserInfo.class), any(BigInteger.class))).thenReturn(true);

        Assert.assertTrue(userController.removeSubscription(BigInteger.ONE, "123"));
    }
}