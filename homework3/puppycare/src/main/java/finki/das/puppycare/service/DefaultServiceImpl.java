package finki.das.puppycare.service;

import finki.das.puppycare.model.PetReport;
import finki.das.puppycare.model.PetType;
import finki.das.puppycare.model.Vet;
import finki.das.puppycare.repository.ReportRepo;
import finki.das.puppycare.repository.VetRepo;
import finki.das.puppycare.service.interfaces.DefaultService;
import org.joda.time.DateTimeZone;
import org.springframework.stereotype.Service;

import org.joda.time.DateTime;

import java.util.List;


@Service
public class DefaultServiceImpl implements DefaultService {
    private final ReportRepo reportRepo;
    private final VetRepo vetRepo;

    public DefaultServiceImpl(ReportRepo reportRepo, VetRepo vetRepo) {
        this.reportRepo = reportRepo;
        this.vetRepo = vetRepo;
    }

    @Override
    public PetReport processReport(double lat, double lon, boolean customerServes, PetType type, Long vetId) {
        DateTime now = new DateTime(DateTimeZone.UTC);

        PetReport petReport = new PetReport(null, now.toDate(), lat, lon, false, customerServes, type, null);

        Vet vet;
        if (vetId == null) {
            vet = vetRepo.nearestVets(lat, lon, 1).get(0);
        } else {
            vet = vetRepo.findById(vetId).orElseThrow(() -> new RuntimeException("Invalid vet id!"));
        }
        petReport.setVet(vet);

        return reportRepo.save(petReport);
    }

    @Override
    public List<Vet> nearVets(double lat, double lon, int count) {
        return vetRepo.nearestVets(lat, lon, count);
    }

    @Override
    public List<Vet> allVets() {
        return vetRepo.findAll();
    }

    @Override
    public List<PetReport> viewReports(Long vetId) {
        Vet vet = new Vet();
        vet.setId(vetId);

        return reportRepo.findByVet(vet);
    }
}
