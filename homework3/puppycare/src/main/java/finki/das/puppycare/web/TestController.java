package finki.das.puppycare.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/test")
@Controller
public class TestController {

    @GetMapping
    public String test(Model model) {
        model.addAttribute("attr1", "hello :D");
        return "welcome";
    }
}
