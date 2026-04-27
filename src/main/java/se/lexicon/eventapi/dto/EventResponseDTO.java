package se.lexicon.eventapi.dto;

import java.time.LocalDateTime;
import java.util.Set;

public record EventResponseDTO(
        Long id,
        String title,
        String description,
        LocalDateTime dateTime,
        String location,
        String status,
        String imageUrl,
        String category,
        Long attendees,
        UserResponseDTO createdBy,
        Set<UserResponseDTO> participants
) {
}
