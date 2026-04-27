package se.lexicon.eventapi.service;

import se.lexicon.eventapi.dto.EventParticipantRequestDTO;
import se.lexicon.eventapi.dto.EventRequestDTO;
import se.lexicon.eventapi.dto.EventResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service Interface for Event-related business logic.
 */
public interface EventService {

    /**
     * Creates a new event.
     * @param eventRequestDto The event request DTO to save.
     * @return The saved event response DTO.
     */
    EventResponseDTO create(EventRequestDTO eventRequestDto);

    /**
     * Finds an event by its ID.
     * @param id The ID of the event.
     * @return An Optional containing the event response DTO if found.
     */
    EventResponseDTO findById(Long id);

    /**
     * Retrieves all events.
     * @return A list of all event response DTOs.
     */
    List<EventResponseDTO> findAll();

    /**
     * Updates an existing event.
     * @param id The ID of the event to update.
     * @param eventRequestDto The event request DTO with updated data.
     * @return The updated event response DTO.
     */
    EventResponseDTO update(Long id, EventRequestDTO eventRequestDto);

    /**
     * Deletes an event by its ID.
     * @param id The ID of the event to delete.
     */
    void deleteById(Long id);

    /**
     * Finds events by title containing a keyword.
     * @param title The keyword to search for.
     * @return A list of matching event response DTOs.
     */
    List<EventResponseDTO> findByTitle(String title);

    /**
     * Finds events within a date range.
     * @param start The start date.
     * @param end The end date.
     * @return A list of event response DTOs in the range.
     */
    List<EventResponseDTO> findByDateRange(LocalDateTime start, LocalDateTime end);

    /**
     * Finds events by location.
     * @param location The location to search for.
     * @return A list of matching event response DTOs.
     */
    List<EventResponseDTO> findByLocation(String location);

    /**
     * Finds events by status.
     * @param status The status to search for.
     * @return A list of matching event response DTOs.
     */
    List<EventResponseDTO> findByStatus(String status);

    /**
     * Adds a participant to an event.
     * @param eventId The ID of the event.
     * @param participantRequestDto The participant details used to resolve or register the user.
     */
    void addParticipant(Long eventId, EventParticipantRequestDTO participantRequestDto);

    /**
     * Removes a participant from an event.
     * @param eventId The ID of the event.
     * @param userId The ID of the user to remove.
     */
    void removeParticipant(Long eventId, Long userId);
}
