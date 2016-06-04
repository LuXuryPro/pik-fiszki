package pik.Util;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.api.*;

import java.security.Principal;

public class FacebookHelper {
    private final Principal principal;

    public FacebookHelper(Principal principal) {
        this.principal = principal;
    }

    public User getFacebookUser()
    {
        OAuth2Authentication u = (OAuth2Authentication)this.principal;
        OAuth2AuthenticationDetails d = (OAuth2AuthenticationDetails)u.getDetails();
        FacebookTemplate f = new FacebookTemplate(d.getTokenValue());
        return f.userOperations().getUserProfile();
    }
}
