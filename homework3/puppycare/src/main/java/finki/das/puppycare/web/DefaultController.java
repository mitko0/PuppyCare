package finki.das.puppycare.web;

import finki.das.puppycare.model.*;
import finki.das.puppycare.service.interfaces.DefaultService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Date;
import java.util.List;

@Controller
public class DefaultController {

    private final DefaultService defaultService;

    public DefaultController(DefaultService defaultService) {
        this.defaultService = defaultService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("types", PetType.values());

        return "index";
    }

    @GetMapping("/oceni")
    public String rate(Model model) {
        model.addAttribute("reports", defaultService.allReports());

        return "test/main";
    }

    @PostMapping("/oceni")
    public String rate(@RequestParam String username,
                       @RequestParam Long reportId,
                       @RequestParam(required = false) String message,
                       @RequestParam float value) {

        RatingKey key = new RatingKey(username, reportId);
        Rating rating = new Rating(key, value, message, null, null);

        defaultService.saveRating(rating);

        return "redirect:/oceni";
    }

    @PostMapping(value = "/prijavi")
    public RedirectView reportPet(@RequestParam String message,
                                  @RequestParam double lat,
                                  @RequestParam double lon,
                                  @RequestParam(required = false, defaultValue = "false") boolean customerServes,
                                  @RequestParam(required = false) PetType type,
                                  @RequestParam(required = false) Long vetId,
                                  RedirectAttributes attr) {

        defaultService.processReport(message, lat, lon, customerServes, type, vetId);

        RedirectView view = new RedirectView("/", true);
        attr.addFlashAttribute("message", true);

        return view;
    }

    @GetMapping("/vet/najdobri")
    public String topN(@RequestParam int count,
                       Model model) {

        List<Rating> ratings = defaultService.rating(PageRequest.of(0, count));
        model.addAttribute("ratings", ratings);
        return "index";
    }

    @GetMapping("/vet/bliski")
    public String showNearVets(@RequestParam double lat,
                               @RequestParam double lon,
                               @RequestParam(required = false, defaultValue = "5") int count,
                               Model model) {

        List<Vet> nearVets = defaultService.nearVets(lat, lon, count);

        model.addAttribute("nearVets", nearVets);
        return "index";
    }

    @GetMapping("/vet")
    public String showAllVets(Model model) {

        List<Vet> vets = defaultService.allVets();

        model.addAttribute("vets", vets);
        return "index";
    }

    @GetMapping("/vet/{vetId}/pregled")
    public String viewReports(@PathVariable Long vetId,
                              Model model) {
        List<PetReport> reports = defaultService.viewReports(vetId);

        model.addAttribute("reports", reports);
        return "index";
    }

    @PostMapping("/milenici/novo")
    public String savePet(@RequestParam String name,
                          @RequestParam PetType type,
                          @RequestParam Long vetId,
                          @RequestParam List<MultipartFile> images) {

        defaultService.savePet(name, type, vetId, images);

        return "redirect:/test";
    }

    @GetMapping("/milenici")
    public String viewPets(Model model) {

        List<Pet> pets = defaultService.allPets();
        model.addAttribute("pets", pets);

        return "test/pets";
    }

    @PostMapping("/milenici/{petId}/termin")
    public String meeting(@RequestParam String username,
                          @PathVariable Long petId,
                          @RequestParam Date date) {

        PetTermKey key = new PetTermKey(username, petId);
        PetTerm term = new PetTerm(key, date, null, null);

        defaultService.createTerm(term);

        return "redirect:/milenici";
    }

}
