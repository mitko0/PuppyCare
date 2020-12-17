package finki.das.puppycare.web;

import finki.das.puppycare.model.Role;
import finki.das.puppycare.model.User;
import finki.das.puppycare.repository.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/sec")
@Controller
public class SecurityController {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    public SecurityController(UserRepo userRepo, BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String main(Model model) {
        model.addAttribute("roles", Role.values());

        return "test/main";
    }

    @PostMapping("/new")
    public String newUser(@ModelAttribute User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);

        return "redirect:/sec";
    }
}
