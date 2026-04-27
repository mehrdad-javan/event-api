package se.lexicon.eventapi.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public record EventRequestDTO(
        @NotBlank(message = "Title is required")
        @Size(max = 255, message = "Title must not exceed 255 characters")
        String title,

        @Size(max = 1000, message = "Description must not exceed 1000 characters")
        String description,

        @NotNull(message = "Date and time are required")
        @FutureOrPresent(message = "Event date must be in the present or future")
        LocalDateTime dateTime,

        @NotBlank(message = "Location is required")
        @Size(max = 255, message = "Location must not exceed 255 characters")
        String location,

        @NotBlank(message = "Status is required")
        String status,

        @NotNull(message = "Creator ID is required")
        Long createdByUserId,

        String imageUrl,
        String category
) {
}
