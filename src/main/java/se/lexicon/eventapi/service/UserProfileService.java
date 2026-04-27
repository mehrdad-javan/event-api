package se.lexicon.eventapi.service;

import se.lexicon.eventapi.dto.UserProfileRequestDTO;
import se.lexicon.eventapi.dto.UserProfileResponseDTO;

import java.util.Optional;

/**
 * Service Interface for UserProfile-related business logic.
 */
public interface UserProfileService {

    /**
     * Updates an existing profile.
     * @param id The ID of the profile to update.
     * @param profileRequestDto The profile request DTO with updated details.
     * @return The updated profile response DTO.
     */
    UserProfileResponseDTO update(Long id, UserProfileRequestDTO profileRequestDto);

    /**
     * Finds a profile by its ID.
     * @param id The ID of the profile.
     * @return An Optional containing the profile response DTO if found.
     */
    Optional<UserProfileResponseDTO> findById(Long id);

}
