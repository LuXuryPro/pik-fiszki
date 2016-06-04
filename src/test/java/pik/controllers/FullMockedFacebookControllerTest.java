package pik.controllers;

import pik.controllers.FacebookController;
import pik.repositories.UserRepository;
import pik.dto.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.UserOperations;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.ui.Model;

import java.security.Principal;

import static org.junit.Assert.*;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({FacebookTemplate.class, FacebookController.class})
public class FullMockedFacebookControllerTest {
    @Test
    public void testFacebookControllerUserNotAuthorized() {
        // Given
        FacebookController facebookController = new FacebookController();
        // When user is not authenticated
        Principal principal = null;
        // Then controller should return LoginPage to user
        assertEquals(facebookController.index(principal, null), "LoginPage");
    }
    @Test
    public void testFacebookControlerNewUserLogin() throws Exception {
        // Given
        // Empty database with no users
        UserRepository userRepository = PowerMockito.mock(UserRepository.class);
        Mockito.when(userRepository.exists(Mockito.any(String.class))).thenReturn(false);
        FacebookController facebookController = new FacebookController(userRepository);
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
        assertEquals(facebookController.index(principal, model), "user");
        // User has been added to repo
        Mockito.verify(userRepository, Mockito.times(1)).save(new UserInfo("1", "Jan", "Testowy", "user@example.com"));
        // Two attributes were passed to model
        Mockito.verify(model, Mockito.times(1)).addAttribute(Mockito.eq("facebookProfile"), Mockito.any(User.class));
        Mockito.verify(model, Mockito.times(1)).addAttribute(Mockito.eq("dataBaseProfile"), Mockito.any(UserInfo.class));

    }
    @Test
    public void testFacebookControlerExistingUserLogin() throws Exception {
        // Given
        // Empty database with no users
        UserRepository userRepository = PowerMockito.mock(UserRepository.class);
        PowerMockito.when(userRepository.exists(Mockito.any(String.class))).thenReturn(true);
        PowerMockito.when(userRepository.findByuserId("1")).thenReturn(new UserInfo("1", "Jan", "Testowy", "user@example.com"));
        FacebookController facebookController = new FacebookController(userRepository);
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
        assertEquals(facebookController.index(principal, model), "user");
        // User has been readed from repo
        Mockito.verify(userRepository, Mockito.times(1)).findByuserId(Mockito.any());
        // Two attributes were passed to model
        Mockito.verify(model, Mockito.times(1)).addAttribute(Mockito.eq("facebookProfile"), Mockito.any(User.class));
        Mockito.verify(model, Mockito.times(1)).addAttribute(Mockito.eq("dataBaseProfile"), Mockito.any(UserInfo.class));
    }
}
