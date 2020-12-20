package finki.das.puppycare.web;

import finki.das.puppycare.model.User;
import finki.das.puppycare.model.Vet;
import finki.das.puppycare.model.enums.Role;
import finki.das.puppycare.service.interfaces.DefaultService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RequestMapping("/sec")
@RestController
public class SecurityController {

    private final DefaultService defaultService;

    public SecurityController(DefaultService defaultService) {
        this.defaultService = defaultService;
    }

    @GetMapping("/najava")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("najava");

        if (isLoggedIn()) {
            modelAndView.setViewName("redirect:/");
        }

        return modelAndView;
    }

    @PostMapping("/registracija")
    public RedirectView register(@Valid @ModelAttribute User user) {

        defaultService.saveUser(user);

        return new RedirectView("/sec/najava");
    }

    @PostMapping("/vraboti")
    public RedirectView employ(@RequestParam List<String> users,
                               @RequestParam Long vet) {

        defaultService.employ(users, vet);

        return new RedirectView("/sec/admin");
    }

    @GetMapping("/admin")
    public ModelAndView admin() {
        ModelAndView modelAndView = new ModelAndView("admin");

        List<Vet> vets = defaultService.allVets();
        List<User> users = defaultService.allUsers();

        modelAndView.getModel().put("users", users);
        modelAndView.getModel().put("vets", vets);

        return modelAndView;
    }

    public static boolean isLoggedIn() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return null != authentication && !("anonymousUser").equals(authentication.getName());
    }
}
