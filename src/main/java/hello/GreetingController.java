package hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting-test";
    }

    @RequestMapping("/testowy")
    public String testowy(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting-test";
    }
    
    @RequestMapping("/login")
    public String login(@RequestParam(value="username", required=true) String username, Model model) {
        model.addAttribute("username", username);
        return "login";
    }

}
