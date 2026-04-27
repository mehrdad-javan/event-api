package se.lexicon.eventapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record UserProfileRequestDTO(
        @NotBlank(message = "Nickname is required")
        @Size(max = 100, message = "Nickname must not exceed 100 characters")
        String nickname,

        @Size(max = 20, message = "Phone number must not exceed 20 characters")
        String phoneNumber,

        @Size(max = 500, message = "Bio must not exceed 500 characters")
        String bio,

        @Size(max = 255, message = "Address must not exceed 255 characters")
        String address
) {
}
