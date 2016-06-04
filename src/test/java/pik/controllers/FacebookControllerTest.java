package pik.controllers;
import pik.dto.UserInfo;
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
        ExtendedModel model = new ExtendedModel();

        String returnValue = FC.user(null, model);
        assertEquals(returnValue, "user");
        assertTrue(model.containsAttribute("facebookProfile"));
        assertTrue(model.containsAttribute("dataBaseProfile"));
        assertEquals(model.getValue("facebookProfile"), "FacebookInfo");
        UserInfo userInfo = (UserInfo) model.getValue("dataBaseProfile");
        assertEquals(userInfo.getUserId(), "1273178312719");
        assertEquals(userInfo.getFirstName(), "Adam");
        assertEquals(userInfo.getLastName(), "Pierwszy");
        assertEquals(userInfo.getEmail(), "a.pierwszy@gmail.com");
    }

    @Test
    public void indexMethodTest()
    {
        ExtendedFacebookController FC = new ExtendedFacebookController();
        ExtendedModel model = new ExtendedModel();

        String returnValue = FC.index(null, model);
        assertEquals(returnValue, "LoginPage");

        ExtendedPrincipal principal = new ExtendedPrincipal();

        returnValue = FC.index(principal, model);
        assertEquals(returnValue, "user");
        assertTrue(model.containsAttribute("facebookProfile"));
        assertTrue(model.containsAttribute("dataBaseProfile"));
        assertEquals(model.getValue("facebookProfile"), "FacebookInfo");
        UserInfo userInfo = (UserInfo) model.getValue("dataBaseProfile");
        assertEquals(userInfo.getUserId(), "1273178312719");
        assertEquals(userInfo.getFirstName(), "Adam");
        assertEquals(userInfo.getLastName(), "Pierwszy");
        assertEquals(userInfo.getEmail(), "a.pierwszy@gmail.com");
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

class ExtendedModel implements Model
{
    private String firstParamName = "facebookProfile";
    private String secondParamName = "dataBaseProfile";
    private String firstParamValue;
    private UserInfo secondParamValue;

    @Override
    public Model addAttribute(String attributeName, Object attributeValue)
    {
        if(attributeName.equals(firstParamName))
        {
            firstParamValue = (String) attributeValue;
        }
        else
        {
            secondParamValue = (UserInfo) attributeValue;
        }
        return this;
    }

    @Override
    public Model addAttribute(Object attributeValue) {
        return null;
    }

    @Override
    public Model addAllAttributes(Collection<?> attributeValues) {
        return null;
    }

    @Override
    public Model addAllAttributes(Map<String, ?> attributes) {
        return null;
    }

    @Override
    public Model mergeAttributes(Map<String, ?> attributes) {
        return null;
    }

    @Override
    public boolean containsAttribute(String attributeName)
    {
        if(attributeName.equals(firstParamName) && firstParamValue != null)
            return true;
        else if(attributeName.equals(secondParamName) && secondParamValue != null)
            return true;
        return false;
    }

    @Override
    public Map<String, Object> asMap() {
        return null;
    }

    public Object getValue(String attributName)
    {
        if(attributName.equals(firstParamName))
            return firstParamValue;
        else if(attributName.equals(secondParamName))
            return secondParamValue;
        else
            return null;
    }
}