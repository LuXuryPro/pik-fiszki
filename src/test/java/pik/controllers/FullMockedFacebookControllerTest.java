package pik.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.UserOperations;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.ui.Model;
import pik.dao.UserDao;

import java.security.Principal;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest({FacebookTemplate.class, IndexController.class})
public class FullMockedFacebookControllerTest {
    @Test
    public void testFacebookControllerUserNotAuthorized() {
        // Given
        IndexController facebookController = new IndexController();
        // When user is not authenticated
        Principal principal = null;
        // Then controller should return LoginPage to user
        assertEquals(facebookController.index(principal, null), "LoginPage");
    }
    @Test
    public void testFacebookControlerNewUserLogin() throws Exception {
        // Given
        // Empty database with no users
        UserController userController = PowerMockito.mock(UserController.class);
        IndexController facebookController = new IndexController(userController);
        // When new (mocked) user got logged in
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

        Model model = PowerMockito.mock(Model.class);
        // Then
        // User was presented by user page
        assertEquals(facebookController.index(principal, model), "redirect:index");
    }
    @Test
    public void testFacebookControlerExistingUserLogin() throws Exception {
        // Given
        // Empty database with no users
        UserController userController = PowerMockito.mock(UserController.class);
        IndexController facebookController = new IndexController(userController);
        // When existing (mocked) user got logged in
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

        Model model = PowerMockito.mock(Model.class);
        // Then
        // User was presented by user page
        assertEquals(facebookController.index(principal, model), "redirect:index");
    }
}
