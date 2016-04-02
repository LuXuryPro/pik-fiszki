package hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

@Controller
public class TestController {

    @RequestMapping(value = "/test", method = RequestMethod.GET,
            produces = "text/html; charset=utf-8")
    @ResponseBody
    public String test(Model model) {
        return "<html><p>Test page content</p></html>";
    }

}
