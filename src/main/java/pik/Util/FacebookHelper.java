package pik.Util;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.api.*;

import java.security.Principal;

/**
 * Klasa pomocnicza udostępniająca interfejs do danych z Facebooka.
 */
public class FacebookHelper {
    private final Principal principal;

    /**
     * Na podstawie obiektu Principal tworzona jest klasa.
     *
     * @param principal Principal
     */
    public FacebookHelper(Principal principal) {
        this.principal = principal;
    }

    /**
     * Funkcja pobierjaąca informacje o użytkowniku z serwisu Facebook. Dostęp do danych podstawowych oraz e-mail.
     *
     * @return Obiekt User.
     */
    public User getFacebookUser() {
        OAuth2Authentication u = (OAuth2Authentication) this.principal;
        OAuth2AuthenticationDetails d = (OAuth2AuthenticationDetails) u.getDetails();
        FacebookTemplate f = new FacebookTemplate(d.getTokenValue());
        return f.userOperations().getUserProfile();
    }

    /**
     * Funkcja pobierająca ID z serwisu Facebook.
     *
     * @return ID użytkownika.
     */
    public String getId() {
        User u = this.getFacebookUser();
        return u.getId();
    }
}
