package finki.das.puppycare.web;

import finki.das.puppycare.model.PetReport;
import finki.das.puppycare.model.PetTerm;
import finki.das.puppycare.model.Rating;
import finki.das.puppycare.model.Vet;
import finki.das.puppycare.model.enums.PetType;
import finki.das.puppycare.model.key.PetTermKey;
import finki.das.puppycare.service.interfaces.DefaultService;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/vet")
@RestController
public class VetController {
    private final DefaultService defaultService;

    public VetController(DefaultService defaultService) {
        this.defaultService = defaultService;
    }

    @GetMapping
    public ModelAndView showAllVets() {
        List<Vet> vets = defaultService.allVets();

        ModelAndView modelAndView = new ModelAndView("veterinarni");
        modelAndView.getModel().put("vets", vets);

        return modelAndView;
    }

    @PreAuthorize("hasAnyRole('ROLE_WORKER', 'ROLE_ADMIN')")
    @GetMapping("/doma")
    public ModelAndView home(Authentication authentication) {
        Long vetId = defaultService.getUser(authentication.getName()).getVet().getId();
        List<PetReport> reports = defaultService.viewReports(vetId);

        ModelAndView modelAndView = new ModelAndView("vet");
        modelAndView.getModel().put("reports", reports);
        modelAndView.getModel().put("types", PetType.values());

        return modelAndView;
    }

    @PreAuthorize("hasAnyRole('ROLE_WORKER', 'ROLE_ADMIN')")
    @GetMapping("/termini")
    public ModelAndView terms(Authentication authentication) {
        Long vetId = defaultService.getUser(authentication.getName()).getVet().getId();
        List<PetTerm> terms = defaultService.getTermsForVet(vetId);

        ModelAndView modelAndView = new ModelAndView("termini");
        modelAndView.getModel().put("terms", terms);

        return modelAndView;
    }

    @PreAuthorize("hasAnyRole('ROLE_WORKER', 'ROLE_ADMIN')")
    @PostMapping("/termini")
    public PetTerm processTerm(@ModelAttribute PetTermKey key,
                               @RequestParam boolean accept) {

        return defaultService.updateTerm(key, accept);
    }

    @GetMapping("/bliski")
    public List<Vet> showNearVets(@RequestParam double lat,
                                  @RequestParam double lon,
                                  @RequestParam(required = false, defaultValue = "5") int count) {

        return defaultService.nearVets(lat, lon, count);
    }

    @GetMapping("/{vetId}/pregled")
    public List<PetReport> viewReports(@PathVariable Long vetId) {
        return defaultService.viewReports(vetId);
    }

    @GetMapping("/najdobri")
    public String topN(@RequestParam int count,
                       Model model) {

        List<Rating> ratings = defaultService.bestVets(PageRequest.of(0, count));
        model.addAttribute("ratings", ratings);
        return "index_";
    }
}
