package finki.das.puppycare.service.interfaces;

import finki.das.puppycare.model.PetReport;
import finki.das.puppycare.model.PetType;
import finki.das.puppycare.model.Vet;

import java.util.List;

public interface DefaultService {
    PetReport processReport(double lat, double lon, boolean customerServes, PetType type, Long vetId);

    List<Vet> nearVets(double lat, double lon, int count);

    List<Vet> allVets();

    List<PetReport> viewReports(Long vetId);
}
