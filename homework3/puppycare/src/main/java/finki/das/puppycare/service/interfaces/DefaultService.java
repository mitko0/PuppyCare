package finki.das.puppycare.service.interfaces;

import finki.das.puppycare.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DefaultService {
    PetReport processReport(String message, double lat, double lon, boolean customerServes, PetType type, Long vetId);

    List<Vet> nearVets(double lat, double lon, int count);

    List<Vet> allVets();

    List<Pet> allPets();

    Pet savePet(String name, PetType type, Long vetId, List<MultipartFile> images);

    List<PetReport> viewReports(Long vetId);

    User saveUser(User user);
}
