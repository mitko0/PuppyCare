package finki.das.puppycare.web;

import finki.das.puppycare.model.Role;
import finki.das.puppycare.model.User;
import finki.das.puppycare.service.interfaces.DefaultService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/sec")
@Controller
public class SecurityController {

    private final DefaultService defaultService;

    public SecurityController(DefaultService defaultService) {
        this.defaultService = defaultService;
    }

    @GetMapping("/najava")
    public String login() {
        return "test/login";
    }

    @PostMapping("/registracija")
    public String register(@Valid @ModelAttribute User user) {

        defaultService.saveUser(user);

        return "redirect:/sec/najava";
    }

    @GetMapping("/registracija")
    public String register(Model model) {
        model.addAttribute("roles", Role.values());

        return "test/main";
    }
}
