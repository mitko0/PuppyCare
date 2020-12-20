package finki.das.puppycare.service;

import finki.das.puppycare.Constants;
import finki.das.puppycare.model.*;
import finki.das.puppycare.model.enums.PetType;
import finki.das.puppycare.model.enums.Role;
import finki.das.puppycare.model.key.PetTermKey;
import finki.das.puppycare.model.projection.RatingView;
import finki.das.puppycare.repository.*;
import finki.das.puppycare.service.interfaces.DefaultService;
import org.joda.time.DateTimeZone;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    private final BCryptPasswordEncoder passwordEncoder;
    private final ReportRepo reportRepo;
    private final VetRepo vetRepo;
    private final PetRepo petRepo;
    private final UserRepo userRepo;
    private final RatingRepo ratingRepo;
    private final PetTermRepo petTermRepo;

    public DefaultServiceImpl(ReportRepo reportRepo, VetRepo vetRepo, PetRepo petRepo, UserRepo userRepo, BCryptPasswordEncoder passwordEncoder, RatingRepo ratingRepo, PetTermRepo petTermRepo) {
        this.reportRepo = reportRepo;
        this.vetRepo = vetRepo;
        this.petRepo = petRepo;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.ratingRepo = ratingRepo;
        this.petTermRepo = petTermRepo;
    }

    @Override
    public PetReport processReport(String title, String message, double lat, double lon, boolean customerServes, PetType type, Long vetId) {
        DateTime now = new DateTime(DateTimeZone.UTC);

        PetReport petReport = new PetReport();
        petReport.setTitle(title);
        petReport.setMessage(message);
        petReport.setDate(now.toDate());
        petReport.setLat(lat);
        petReport.setLon(lon);
        petReport.setCustomerServes(customerServes);
        petReport.setType(type);

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
    public PetReport updateReport(Long id, boolean done) {
        PetReport report = reportRepo.findById(id).orElseThrow(() -> new RuntimeException("Invalid id"));
        report.setDone(done);
        return reportRepo.save(report);
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
    public Pet getPet(Long id) {
        return petRepo.findById(id).orElseThrow(() -> new RuntimeException("Invalid id"));
    }

    @Override
    public List<Pet> allPets() {
        return petRepo.findAll(Sort.by(Sort.Order.by("vet.id")));
    }

    @Override
    public Pet savePet(String name, PetType type, Vet vet, List<MultipartFile> images) {
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
    public List<PetReport> allReports() {
        return reportRepo.findAll();
    }

    @Override
    public List<PetReport> viewReports(Long vetId) {
        Vet vet = new Vet();
        vet.setId(vetId);

        return reportRepo.findByVet(vet);
    }

    @Override
    public PetReport viewReport(Long reportId) {
        return reportRepo.findById(reportId).orElseThrow(() -> new RuntimeException("Invalid id"));
    }

    @Override
    public User getUser(String username) {
        return userRepo.findById(username).get();
    }

    @Override
    public User saveUser(User user) {
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.ROLE_USER);

        if (userRepo.count() == 0) {
            user.setRole(Role.ROLE_ADMIN);
        }

        return userRepo.save(user);
    }

    @Override
    public List<User> allUsers() {
        return userRepo.findAll();
    }

    @Override
    public Rating saveRating(Rating rating) {
        return ratingRepo.save(rating);
    }

    @Override
    public List<Rating> ratingsForReport(Long reportId) {
        return ratingRepo.findRatingById_ReportId(reportId);
    }

    @Override
    public List<Rating> bestVets(Pageable pageable) {
        return ratingRepo.findAverageRating(pageable);
    }

    @Override
    public PetTerm createTerm(PetTerm term) {
        DateTime dateTime = new DateTime(term.getDate(), DateTimeZone.UTC);
        term.setDate(dateTime.toDate());

        return petTermRepo.save(term);
    }

    @Override
    public PetTerm updateTerm(PetTermKey key, boolean accept) {
        PetTerm term = petTermRepo.findById(key).orElseThrow();
        term.setSeen(true);

        if (accept) {
            User owner = userRepo.findById(key.getOwnerId()).orElseThrow();
            term.getPet().setOwner(owner);
        }

        return petTermRepo.save(term);
    }

    @Override
    public List<PetTerm> getTermsForVet(Long vetId) {
        return petTermRepo.findPetTermsForVet(vetId);
    }

    @Override
    public List<RatingView> asd() {
        return ratingRepo.findAverageRating();
    }

    @Override
    public void employ(List<String> usernames, Long vetId) {
        List<User> users = userRepo.findAllById(usernames);
        users.forEach(user -> {
            Vet vet = new Vet();
            vet.setId(vetId);

            user.setVet(vet);
            user.setRole(Role.ROLE_WORKER);
        });

        userRepo.saveAll(users);
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
