package finki.das.puppycare.web;

import finki.das.puppycare.model.Pet;
import finki.das.puppycare.model.PetTerm;
import finki.das.puppycare.model.Vet;
import finki.das.puppycare.model.enums.PetType;
import finki.das.puppycare.model.key.PetTermKey;
import finki.das.puppycare.service.interfaces.DefaultService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@RequestMapping("/milenici")
@RestController
public class PetController {

    private final DefaultService defaultService;

    public PetController(DefaultService defaultService) {
        this.defaultService = defaultService;
    }

    @GetMapping
    public ModelAndView viewPets() {
        ModelAndView modelAndView = new ModelAndView("milenici");

        List<Pet> pets = defaultService.allPets();
        modelAndView.getModel().put("pets", pets);

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView viewPet(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("milenik");

        Pet pet = defaultService.getPet(id);
        modelAndView.getModel().put("pet", pet);

        return modelAndView;
    }

    @PreAuthorize("hasAnyRole('WORKER', 'ADMIN')")
    @PostMapping("/novo")
    public Pet savePet(@RequestParam String name,
                       @RequestParam PetType type,
                       @RequestParam List<MultipartFile> images,
                       Authentication authentication) {

        Vet vet = defaultService.getUser(authentication.getName()).getVet();

        return defaultService.savePet(name, type, vet, images);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{petId}/termin")
    public PetTerm meeting(@PathVariable Long petId,
                           @RequestParam Date date,
                           Authentication authentication) {

        String username = authentication.getName();

        PetTermKey key = new PetTermKey(username, petId);
        PetTerm term = new PetTerm(key, false, date, null, null);

        return defaultService.createTerm(term);
    }

}
