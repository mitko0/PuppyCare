package finki.das.puppycare.web;

import finki.das.puppycare.model.PetReport;
import finki.das.puppycare.model.PetType;
import finki.das.puppycare.model.Vet;
import finki.das.puppycare.service.interfaces.DefaultService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class DefaultController {

    private final DefaultService defaultService;

    public DefaultController(DefaultService defaultService) {
        this.defaultService = defaultService;
    }

    @PostMapping(value = "/prijavi")
    public RedirectView reportPet(@RequestParam double lat,
                                  @RequestParam double lon,
                                  @RequestParam(required = false, defaultValue = "false") boolean customerServes,
                                  @RequestParam(required = false) PetType type,
                                  @RequestParam(required = false) Long vetId,
                                   RedirectAttributes attr) {

        defaultService.processReport(lat, lon, customerServes, type, vetId);

        RedirectView view = new RedirectView("/", true);
        attr.addFlashAttribute("message", true);

        return view;
    }

    @GetMapping("/vet/bliski")
    public String showNearVets(@RequestParam double lat,
                               @RequestParam double lon,
                               @RequestParam(required = false, defaultValue = "5") int count,
                               Model model) {

        List<Vet> nearVets = defaultService.nearVets(lat, lon, count);

        model.addAttribute("nearVets", nearVets);
        return "near";
    }

    @GetMapping("/vet")
    public String showAllVets(Model model) {

        List<Vet> vets = defaultService.allVets();

        model.addAttribute("vets", vets);
        return "all";
    }

    @GetMapping("/vet/{vetId}/pregled")
    public String viewReports(@PathVariable Long vetId,
                              Model model) {
        List<PetReport> reports = defaultService.viewReports(vetId);

        model.addAttribute("reports", reports);
        return "asd";
    }
}
