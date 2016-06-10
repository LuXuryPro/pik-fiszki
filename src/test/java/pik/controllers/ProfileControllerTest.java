package pik.controllers;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.UserOperations;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pik.JSONDao.EditProfileJsonRequest;
import pik.JSONDao.EditProfileJsonResponse;
import pik.Util.FacebookHelper;
import pik.dao.UserDao;
import pik.dto.CourseInfo;
import pik.dto.UserInfo;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(PowerMockRunner.class)
@PrepareForTest({FacebookTemplate.class, DoCourseControlerTest.class, DoCourseControler.class, FacebookHelper.class})
public class ProfileControllerTest {
    private MockMvc mockMvc;
    private QuestionsController questionsController;
    private UserDao userDao;
    private CourseController courseController;

    @Before
    public void setup() {
        this.userDao = Mockito.mock(UserDao.class);
        this.courseController = Mockito.mock(CourseController.class);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new ProfileController(userDao, courseController)).build();
    }
    @Test
    public void showProfile() throws Exception {
        OAuth2AuthenticationDetails oAuth2AuthenticationDetails = PowerMockito.mock(OAuth2AuthenticationDetails.class);
        PowerMockito.when(oAuth2AuthenticationDetails.getTokenValue()).thenReturn("123456789");

        OAuth2Authentication principal = PowerMockito.mock(OAuth2Authentication.class);
        PowerMockito.when(principal.getDetails()).thenReturn(oAuth2AuthenticationDetails);

        User user = PowerMockito.mock(User.class);
        PowerMockito.when(user.getId()).thenReturn("1");
        PowerMockito.when(user.getFirstName()).thenReturn("Jan");
        PowerMockito.when(user.getLastName()).thenReturn("Testowy");
        PowerMockito.when(user.getEmail()).thenReturn("user@example.com");

        FacebookTemplate facebookTemplate = PowerMockito.mock(FacebookTemplate.class);
        UserOperations userOperations = PowerMockito.mock(UserOperations.class);
        PowerMockito.when(userOperations.getUserProfile()).thenReturn(user);
        PowerMockito.when(facebookTemplate.userOperations()).thenReturn(userOperations);
        PowerMockito.whenNew(FacebookTemplate.class).withAnyArguments().thenReturn(facebookTemplate);

        UserInfo userInfo = Mockito.mock(UserInfo.class);
        Mockito.when(this.userDao.getById("1")).thenReturn(userInfo);

        CourseInfo courseInfo = Mockito.mock(CourseInfo.class);
        List<CourseInfo> courseInfos = new LinkedList<CourseInfo>();
        courseInfos.add(courseInfo);
        Mockito.when(this.courseController.getUserCourses("1"))
                .thenReturn(courseInfos);

        CourseInfo courseInfoSub = Mockito.mock(CourseInfo.class);
        List<CourseInfo> courseInfoSubs = new LinkedList<CourseInfo>();
        courseInfoSubs.add(courseInfoSub);
        Mockito.when(this.courseController.getSubscribedCourses("1"))
                .thenReturn(courseInfoSubs);
        mockMvc.perform(get("/me").principal(principal))
                .andExpect(status().isOk())
                .andExpect(model().attribute("userCourses", 1))
                .andExpect(model().attribute("userSubs", 1))
                .andExpect(model().attribute("user", userInfo));

    }

    @Test
    public void editProfile() throws Exception {
        OAuth2AuthenticationDetails oAuth2AuthenticationDetails = PowerMockito.mock(OAuth2AuthenticationDetails.class);
        PowerMockito.when(oAuth2AuthenticationDetails.getTokenValue()).thenReturn("123456789");

        OAuth2Authentication principal = PowerMockito.mock(OAuth2Authentication.class);
        PowerMockito.when(principal.getDetails()).thenReturn(oAuth2AuthenticationDetails);

        User user = PowerMockito.mock(User.class);
        PowerMockito.when(user.getId()).thenReturn("1");
        PowerMockito.when(user.getFirstName()).thenReturn("Jan");
        PowerMockito.when(user.getLastName()).thenReturn("Testowy");
        PowerMockito.when(user.getEmail()).thenReturn("user@example.com");

        FacebookTemplate facebookTemplate = PowerMockito.mock(FacebookTemplate.class);
        UserOperations userOperations = PowerMockito.mock(UserOperations.class);
        PowerMockito.when(userOperations.getUserProfile()).thenReturn(user);
        PowerMockito.when(facebookTemplate.userOperations()).thenReturn(userOperations);
        PowerMockito.whenNew(FacebookTemplate.class).withAnyArguments().thenReturn(facebookTemplate);

        UserInfo userInfo = Mockito.mock(UserInfo.class);
        Mockito.when(this.userDao.getById("1")).thenReturn(userInfo);

        mockMvc.perform(get("/preferences").principal(principal))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", userInfo));
    }

    @Test
    public void addPerson() throws Exception {
        OAuth2AuthenticationDetails oAuth2AuthenticationDetails = PowerMockito.mock(OAuth2AuthenticationDetails.class);
        PowerMockito.when(oAuth2AuthenticationDetails.getTokenValue()).thenReturn("123456789");

        OAuth2Authentication principal = PowerMockito.mock(OAuth2Authentication.class);
        PowerMockito.when(principal.getDetails()).thenReturn(oAuth2AuthenticationDetails);

        User user = PowerMockito.mock(User.class);
        PowerMockito.when(user.getId()).thenReturn("1");
        PowerMockito.when(user.getFirstName()).thenReturn("Jan");
        PowerMockito.when(user.getLastName()).thenReturn("Testowy");
        PowerMockito.when(user.getEmail()).thenReturn("user@example.com");

        FacebookTemplate facebookTemplate = PowerMockito.mock(FacebookTemplate.class);
        UserOperations userOperations = PowerMockito.mock(UserOperations.class);
        PowerMockito.when(userOperations.getUserProfile()).thenReturn(user);
        PowerMockito.when(facebookTemplate.userOperations()).thenReturn(userOperations);
        PowerMockito.whenNew(FacebookTemplate.class).withAnyArguments().thenReturn(facebookTemplate);

        EditProfileJsonRequest request = new EditProfileJsonRequest();
        request.setFirstName("Jan");
        request.setSecondName("Kowalski");
        request.setEmail("user@example.com");
        request.setId("1");

        Gson gson = new Gson();
        String json = gson.toJson(request);

        UserInfo userInfo = Mockito.mock(UserInfo.class);
        Mockito.when(this.userDao.getById("1")).thenReturn(userInfo);

        MvcResult result = mockMvc.perform(post("/saveUserForm").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk()).andReturn();
        gson = new Gson();
        EditProfileJsonResponse response = gson.fromJson(result.getResponse().getContentAsString(), EditProfileJsonResponse.class);
        assertEquals(response.getStatus(), "OK");
        Mockito.verify(this.userDao, times(1)).getById("1");
        Mockito.verify(userInfo, times(1)).setFirstName("Jan");
        Mockito.verify(userInfo, times(1)).setLastName("Kowalski");
        Mockito.verify(userInfo, times(1)).setEmail("user@example.com");
        Mockito.verify(this.userDao, times(1)).update(userInfo);
    }
}