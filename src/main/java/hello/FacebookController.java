package hello;

import dto.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@EnableOAuth2Sso
@Controller
@ComponentScan(basePackages = {"repositories"})
public class FacebookController extends WebSecurityConfigurerAdapter {

    UserRepository users;

    FacebookController() {

    }

    @Autowired
    FacebookController(UserRepository users)
    {
        this.users = users;
    }

    @RequestMapping("/user")
    public String user(Principal principal, Model model) {
        getUserInfo(principal, model);
        return "user";
    }
    @RequestMapping("/")
    public String index(Principal principal, Model model) {
        if (principal == null)
            return "LoginPage";
        else {
            getUserInfo(principal, model);
            return "user";
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**").authorizeRequests()
                .antMatchers("/", "/login**", "/webjars/**", "/mappings").permitAll()
                .anyRequest().authenticated()
                .and().logout().logoutSuccessUrl("/").permitAll()
                .and().csrf().csrfTokenRepository(csrfTokenRepository())
                .and().addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);

    }

    protected void getUserInfo(Principal principal, Model model) {
        OAuth2Authentication u = (OAuth2Authentication)principal;
        OAuth2AuthenticationDetails d = (OAuth2AuthenticationDetails)u.getDetails();
        FacebookTemplate f = new FacebookTemplate(d.getTokenValue());
        User usr = f.userOperations().getUserProfile();

        UserInfo userData;
        if(!users.exists(usr.getId())){
            userData = new UserInfo(usr.getId(),usr.getFirstName(),usr.getLastName(),usr.getEmail(),"username");
            users.save(userData);
        }
        else
            userData = users.findByuserId(usr.getId());

        model.addAttribute("facebookProfile", f.userOperations().getUserProfile());
        model.addAttribute("dataBaseProfile", userData);


    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }

    private Filter csrfHeaderFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain) throws ServletException, IOException {
                CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
                if (csrfToken != null) {
                    Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
                    String token = csrfToken.getToken();
                    if (cookie == null || token != null && !token.equals(cookie.getValue())) {
                        cookie = new Cookie("XSRF-TOKEN", token);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                    }
                }
                filterChain.doFilter(request, response);
            }
        };
    }
}
