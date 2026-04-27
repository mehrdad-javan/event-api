package se.lexicon.eventapi.dto;

import java.time.Instant;
public record UserResponseDTO(
        Long id,
        String email,
        String fullName,
        Instant createDate
) {
}
