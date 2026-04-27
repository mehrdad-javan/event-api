package se.lexicon.eventapi.service;

import se.lexicon.eventapi.dto.UserRequestDTO;
import se.lexicon.eventapi.dto.UserResponseDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for User-related business logic.
 */
public interface UserService {

    /**
     * Registers a new user.
     * @param userRequestDto The user request DTO to save.
     * @return The saved user response DTO.
     */
    UserResponseDTO register(UserRequestDTO userRequestDto);

    /**
     * Finds a user by their ID.
     * @param id The ID of the user.
     * @return An Optional containing the user response DTO if found.
     */
    Optional<UserResponseDTO> findById(Long id);

    /**
     * Retrieves all users.
     * @return A list of all user response DTOs.
     */
    List<UserResponseDTO> findAll();

    /**
     * Deletes a user by their ID.
     * @param id The ID of the user to delete.
     */
    void deleteById(Long id);
}
