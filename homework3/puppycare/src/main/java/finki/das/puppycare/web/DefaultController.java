package finki.das.puppycare.web;

import finki.das.puppycare.model.enums.PetType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@RequestMapping("/")
@RestController
public class DefaultController {

    @GetMapping
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        mv.getModel().put("types", PetType.values());

        return mv;
    }
}
