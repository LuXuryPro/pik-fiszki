package hello;
import dto.UserInfo;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.social.facebook.api.User;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.Collection;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class FacebookControllerTest
{
    @Test
    public void userMethodTest()
    {
        ExtendedFacebookController FC = new ExtendedFacebookController();
        Model model = Mockito.mock(Model.class);

        String returnValue = FC.user(null, model);
        assertEquals(returnValue, "user");
    }

    @Test
    public void indexMethodTest()
    {
        ExtendedFacebookController FC = new ExtendedFacebookController();
        Model model = Mockito.mock(Model.class);

        String returnValue = FC.index(null, model);
        assertEquals(returnValue, "LoginPage");

        ExtendedPrincipal principal = new ExtendedPrincipal();

        returnValue = FC.index(principal, model);
        assertEquals(returnValue, "user");
    }
}

class ExtendedFacebookController extends FacebookController
{
    @Override
    protected void getUserInfo(Principal principal, Model model)
    {
        User usr = Mockito.mock(User.class);
        when(usr.getId()).thenReturn("1273178312719");
        when(usr.getFirstName()).thenReturn("Adam");
        when(usr.getLastName()).thenReturn("Pierwszy");
        when(usr.getEmail()).thenReturn("a.pierwszy@gmail.com");
        UserInfo userData;

        userData = new UserInfo(usr.getId(),usr.getFirstName(),usr.getLastName(),usr.getEmail());

        model.addAttribute("facebookProfile", "FacebookInfo");
        model.addAttribute("dataBaseProfile", userData);
    }
}

class ExtendedPrincipal implements Principal
{
    @Override
    public String getName() {
        return null;
    }
}