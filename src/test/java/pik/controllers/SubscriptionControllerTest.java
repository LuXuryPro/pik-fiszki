package pik.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pik.dao.UserDao;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SubscriptionControllerTest {

    private UserController userController;
    private CourseController courseController;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.userController = Mockito.mock(UserController.class);
        this.courseController = Mockito.mock(CourseController.class);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new SubscriptionController(userController, courseController)).build();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void addSubscription() throws Exception {
        mockMvc.perform(get("/add-sub"))
                .andExpect(status().isOk());
    }

    @Test
    public void showAllCourses() throws Exception {

    }

    @Test
    public void removeSubscription() throws Exception {

    }

    @Test
    public void doAddCourse() throws Exception {

    }

    @Test
    public void deleteSubscription() throws Exception {

    }

}