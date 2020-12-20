package finki.das.puppycare.web;

import finki.das.puppycare.model.PetReport;
import finki.das.puppycare.model.Rating;
import finki.das.puppycare.model.enums.PetType;
import finki.das.puppycare.model.key.RatingKey;
import finki.das.puppycare.service.interfaces.DefaultService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RequestMapping("/prijava")
@RestController
public class PetReportController {

    private final DefaultService defaultService;

    public PetReportController(DefaultService defaultService) {
        this.defaultService = defaultService;
    }

    @PostMapping
    public boolean reportPet(@RequestParam String title,
                             @RequestParam String message,
                             @RequestParam double lat,
                             @RequestParam double lon,
                             @RequestParam(required = false, defaultValue = "false") boolean customerServes,
                             @RequestParam(required = false) PetType type,
                             @RequestParam(required = false) Long vetId) {

        try {
            defaultService.processReport(title, message, lat, lon, customerServes, type, vetId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @PostMapping("/azur")
    public RedirectView updateReport(@RequestParam Long id,
                                     @RequestParam(required = false, defaultValue = "false") boolean done) {

        defaultService.updateReport(id, done);

        return new RedirectView("/vet/doma");
    }

    @GetMapping("/{reportId}")
    public ModelAndView viewReport(@PathVariable Long reportId) {

        PetReport report = defaultService.viewReport(reportId);
        List<Rating> ratings = defaultService.ratingsForReport(reportId);

        ModelAndView modelAndView = new ModelAndView("report");
        modelAndView.getModel().put("report", report);
        modelAndView.getModel().put("ratings", ratings);

        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{reportId}/oceni")
    public RedirectView rate(@PathVariable Long reportId,
                             @RequestParam String username,
                             @RequestParam(required = false) String message,
                             @RequestParam float value) {

        RatingKey key = new RatingKey(username, reportId);
        Rating rating = new Rating(key, value, message, null, null);

        defaultService.saveRating(rating);

        return new RedirectView("/prijava/" + reportId);
    }
}
