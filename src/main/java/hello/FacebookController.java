package hello;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.token.Token;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

@EnableOAuth2Sso
@Controller
public class FacebookController extends WebSecurityConfigurerAdapter {
    @RequestMapping("/user")
    public String user(Principal principal, Model model) {
        OAuth2Authentication u = (OAuth2Authentication)principal;
        OAuth2AuthenticationDetails d = (OAuth2AuthenticationDetails)u.getDetails();
        Facebook f = new FacebookTemplate(d.getTokenValue());
        model.addAttribute("facebookProfile", f.userOperations().getUserProfile());
        return "user";
    }
    @RequestMapping("/")
    public String index(Principal principal, Model model) {
        if (principal == null)
            return "LoginPage";
        else {
            OAuth2Authentication u = (OAuth2Authentication) principal;
            OAuth2AuthenticationDetails d = (OAuth2AuthenticationDetails) u.getDetails();
            Facebook f = new FacebookTemplate(d.getTokenValue());
            model.addAttribute("facebookProfile", f.userOperations().getUserProfile());
            return "user";
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**").authorizeRequests()
                .antMatchers("/", "/login**", "/webjars/**", "/mappings").permitAll()
                .anyRequest().authenticated()
                .and().logout().logoutSuccessUrl("/").permitAll();

    }
}
