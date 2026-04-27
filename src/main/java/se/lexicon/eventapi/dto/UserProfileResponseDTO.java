package se.lexicon.eventapi.dto;


public record UserProfileResponseDTO(
        Long id,
        String nickname,
        String phoneNumber,
        String bio,
        String address
) {
}
