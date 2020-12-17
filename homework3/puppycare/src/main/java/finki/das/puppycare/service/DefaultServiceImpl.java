package finki.das.puppycare.service;

import finki.das.puppycare.Constants;
import finki.das.puppycare.model.*;
import finki.das.puppycare.repository.PetRepo;
import finki.das.puppycare.repository.ReportRepo;
import finki.das.puppycare.repository.UserRepo;
import finki.das.puppycare.repository.VetRepo;
import finki.das.puppycare.service.interfaces.DefaultService;
import org.joda.time.DateTimeZone;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.joda.time.DateTime;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@SuppressWarnings({"ConstantConditions", "ResultOfMethodCallIgnored"})
@Service
public class DefaultServiceImpl implements DefaultService {
    private final ReportRepo reportRepo;
    private final VetRepo vetRepo;
    private final PetRepo petRepo;
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    public DefaultServiceImpl(ReportRepo reportRepo, VetRepo vetRepo, PetRepo petRepo, UserRepo userRepo, BCryptPasswordEncoder passwordEncoder) {
        this.reportRepo = reportRepo;
        this.vetRepo = vetRepo;
        this.petRepo = petRepo;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public PetReport processReport(String message, double lat, double lon, boolean customerServes, PetType type, Long vetId) {
        DateTime now = new DateTime(DateTimeZone.UTC);

        PetReport petReport = new PetReport(null, message, now.toDate(), lat, lon, false, customerServes, type, null);

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
    public List<Pet> allPets() {
        return petRepo.findAll();
    }

    @Override
    public Pet savePet(String name, PetType type, Long vetId, List<MultipartFile> images) {
        Vet vet = vetRepo.findById(vetId).orElseThrow(() -> new RuntimeException("Invalid vet id"));

        Pet pet = new Pet();
        pet.setVet(vet);
        pet.setName(name);
        pet.setType(type);
        pet.setImagesLocation("/" + name);

        try {
            for (MultipartFile image : images) {
                if (image.getContentType().startsWith("image/"))
                    saveFile(image, name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return petRepo.save(pet);
    }

    @Override
    public List<PetReport> viewReports(Long vetId) {
        Vet vet = new Vet();
        vet.setId(vetId);

        return reportRepo.findByVet(vet);
    }

    @Override
    public User saveUser(User user) {
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));

        return userRepo.save(user);
    }

    private void saveFile(MultipartFile file, String location) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());


        Path path = Paths.get(Constants.fileBasePath, location, fileName);
        File tmpFile = new File(path.toString());
        tmpFile.mkdirs();
        tmpFile.createNewFile();
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
    }
}
