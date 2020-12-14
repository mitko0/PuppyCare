package finki.das.puppycare.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/test")
@Controller
public class TestController {

    @GetMapping
    public String test() {
        return "welcome";
    }
}
