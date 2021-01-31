package finki.das.puppycare.service.interfaces;

import finki.das.puppycare.model.*;
import finki.das.puppycare.model.enums.PetType;
import finki.das.puppycare.model.key.PetTermKey;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DefaultService {
    /**
     * Creates new PetReport instance and saves it to the database.
     *
     * @param title          Title of the report.
     * @param message        Message of the report.
     * @param lat            Location (latitude) of the reported pet.
     * @param lon            Location (longitude) of the reported pet.
     * @param customerServes Boolean value denoting whether the person will bring the reported pet in the vet or not.
     * @param type           The type of the pet.
     * @param vetId          The id of the vet that should process the pet.
     * @return The newly created and saved PetReport.
     * @see PetReport
     * @see PetType
     */
    PetReport processReport(String title, String message, double lat, double lon, boolean customerServes, PetType type, Long vetId);

    /**
     * Change the status of a PetReport.
     *
     * @param id   The id of the PetReport.
     * @param done the status of the report.
     * @return The updated report.
     * @see PetReport
     */
    PetReport updateReport(Long id, boolean done);

    /**
     * Get the nearest vets.
     *
     * @param lat   Latitude to search near.
     * @param lon   Longitude to search near.
     * @param count Maximum number of vets to select.
     * @return List of found vets.
     * @see List
     */
    List<Vet> nearVets(double lat, double lon, int count);

    /**
     * @return All vets in the database
     */
    List<Vet> allVets();

    /**
     * Finds a pet.
     *
     * @param id The id of the pet.
     * @return The found pet.
     * @throws RuntimeException If no user with this id is found.
     * @see RuntimeException
     */
    Pet getPet(Long id);

    /**
     * @return All pets in the database
     */
    List<Pet> allPets();

    /**
     * Saves a new Pet to the database.
     *
     * @param name   The name of the pet.
     * @param type   The type of the pet.
     * @param vet    The vet that is responsible for the pet.
     * @param images List of images from the pet.
     * @return The newly created pet.
     * @see List
     */
    Pet savePet(String name, PetType type, Vet vet, List<MultipartFile> images);

    /**
     * @return All pet reports in the database.
     */
    List<PetReport> allReports();

    /**
     * Finds all reports sent to a vet.
     *
     * @param vetId The id of the vet.
     * @return List of PetReport
     */
    List<PetReport> viewReports(Long vetId);

    /**
     * Finds a PetReport in the database.
     *
     * @param reportId The id to search for.
     * @return The found PetReport.
     * @throws RuntimeException If no PetReport with the specific id is found.
     * @see RuntimeException
     */
    PetReport viewReport(Long reportId);

    /**
     * Finds a User in the database.
     *
     * @param username The username to search for.
     * @return The found User.
     * @throws RuntimeException If no User with the specific id is found.
     * @see RuntimeException
     */
    User getUser(String username);

    /**
     * Saves a new User to the database.
     *
     * @param user The user to be saved.
     * @return The newly created user.
     */
    User saveUser(User user);

    /**
     * Gets all the users in the database.
     *
     * @return List of all the users.
     * @see List
     */
    List<User> allUsers();

    /**
     * Saves a new Rating to the database.
     *
     * @param rating The rating to be saved.
     * @return The newly created rating.
     */
    Rating saveRating(Rating rating);

    /**
     * Gets all the ratings for a report.
     *
     * @param reportId The id of the report.
     * @return List of all ratings for the report.
     * @see List
     */
    List<Rating> ratingsForReport(Long reportId);

    /**
     * Finds vets with best ratings.
     *
     * @param pageable The criteria for best.
     * @return List of the found ratings.
     * @see List
     */
    List<Rating> bestVets(Pageable pageable);

    /**
     * Saves a new PetTerm to the database.
     *
     * @param term The term to be saved.
     * @return The newly created PetTerm.
     */
    PetTerm createTerm(PetTerm term);

    /**
     * Set the owner of the Pet.
     *
     * @param key    The id of the PetTerm.
     * @param accept .
     * @return The updated PetTerm.
     */
    PetTerm updateTerm(PetTermKey key, boolean accept);

    /**
     * Gets all PetTerms for a vet.
     *
     * @param vetId The id of the vet.
     * @return List of the found PetTerms.
     * @see List
     */
    List<PetTerm> getTermsForVet(Long vetId);

    /**
     * Sets the workplace of the users.
     *
     * @param usernames The list of users to employ in the vet.
     * @param vetId     The id of the vet.
     */
    void employ(List<String> usernames, Long vetId);
}
