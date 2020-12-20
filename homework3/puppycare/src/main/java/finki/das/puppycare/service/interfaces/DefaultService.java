package finki.das.puppycare.service.interfaces;

import finki.das.puppycare.model.*;
import finki.das.puppycare.model.enums.PetType;
import finki.das.puppycare.model.key.PetTermKey;
import finki.das.puppycare.model.projection.RatingView;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DefaultService {
    PetReport processReport(String title, String message, double lat, double lon, boolean customerServes, PetType type, Long vetId);

    PetReport updateReport(Long id, boolean done);

    List<Vet> nearVets(double lat, double lon, int count);

    List<Vet> allVets();

    Pet getPet(Long id);

    List<Pet> allPets();

    Pet savePet(String name, PetType type, Vet vet, List<MultipartFile> images);

    List<PetReport> allReports();

    List<PetReport> viewReports(Long vetId);

    PetReport viewReport(Long reportId);

    User getUser(String username);

    User saveUser(User user);

    List<User> allUsers();

    Rating saveRating(Rating rating);

    List<Rating> ratingsForReport(Long reportId);

    List<Rating> bestVets(Pageable pageable);

    PetTerm createTerm(PetTerm term);

    PetTerm updateTerm(PetTermKey key, boolean accept);

    List<PetTerm> getTermsForVet(Long vetId);

    List<RatingView> asd();

    void employ(List<String> usernames, Long vetId);
}
