package pik.controllers;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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
import org.springframework.test.web.servlet.MockMvc.*;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pik.JSONDao.DoCourseControlerClientAnswer;
import pik.JSONDao.DoCourseControlerFishe;
import pik.JSONDao.DoCourseControllerClientFicheRequest;
import pik.Util.FacebookHelper;
import pik.dto.QuestionInfo;
import pik.exceptions.CourseAccessException;

import java.lang.reflect.Type;
import java.math.BigInteger;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(PowerMockRunner.class)
@PrepareForTest({FacebookTemplate.class, DoCourseControlerTest.class, DoCourseControler.class, FacebookHelper.class})
public class DoCourseControlerTest {
    private MockMvc mockMvc;
    private QuestionsController questionsController;

    @Before
    public void setup() throws CourseAccessException {
        this.questionsController = Mockito.mock(QuestionsController.class);
        QuestionInfo questionInfo = Mockito.mock(QuestionInfo.class);
        Mockito.when(questionInfo.getId()).thenReturn(BigInteger.valueOf(10));
        Mockito.when(questionInfo.getAnswer()).thenReturn("answer");
        Mockito.when(questionInfo.getQuestion()).thenReturn("question");
        Mockito.when(this.questionsController.getFirstQuestion(Mockito.anyString(), Mockito.any())).thenReturn(questionInfo);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new DoCourseControler(questionsController)).build();
    }

    @Test
    public void testDoCourse() throws Exception {
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

        mockMvc.perform(get("/do-course/10").principal(principal))
                .andExpect(status().isOk());
    }

    @Test
    public void testPrecessRequest() throws Exception {
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

        DoCourseControllerClientFicheRequest request = new DoCourseControllerClientFicheRequest();
        request.setCourseId(BigInteger.valueOf(1));
        request.setUserId("1");

        Gson gson = new Gson();
        String json = gson.toJson(request);

        MvcResult result = mockMvc.perform(post("/getfishe").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk()).andReturn();
        gson = new Gson();
        DoCourseControlerFishe fiche = gson.fromJson(result.getResponse().getContentAsString(), (Type) DoCourseControlerFishe.class);
        assertEquals(fiche.getFace(), "question");
        assertEquals(fiche.getBack(), "answer");
        assertEquals(fiche.getCourseId(), BigInteger.ONE);
        assertEquals(fiche.getQuestionId(), BigInteger.valueOf(10));
    }

    @Test
    public void testPrecessAnswer() throws Exception {
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

        DoCourseControlerClientAnswer request = new DoCourseControlerClientAnswer();
        request.setCourseId(BigInteger.valueOf(1));
        request.setMark(5);
        request.setQuestionId(BigInteger.ONE);

        Gson gson = new Gson();
        String json = gson.toJson(request);

        MvcResult result = mockMvc.perform(post("/answer").principal(principal).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk()).andReturn();
        QuestionInfo qi = new QuestionInfo();
        qi.setCourseId(BigInteger.valueOf(1));
        qi.setId(BigInteger.valueOf(1));

        Mockito.verify(this.questionsController, times(1)).markQuestion(qi, "1", 5);
    }


}