package se.lexicon.eventapi.mapper;

import org.springframework.stereotype.Component;
import se.lexicon.eventapi.dto.*;
import se.lexicon.eventapi.entity.Event;
import se.lexicon.eventapi.entity.EventStatus;
import se.lexicon.eventapi.entity.User;
import se.lexicon.eventapi.entity.UserProfile;

import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Component for mapping between entities and DTOs.
 */
@Component
public class EntityDtoMapper {

    // User mapping
    public UserResponseDTO toUserResponseDto(User user) {
        if (user == null) return null;
        return new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getCreateDate()
        );
    }

    public User toUserEntity(UserRequestDTO dto) {
        if (dto == null) return null;
        User user = new User();
        user.setEmail(dto.email());
        user.setFullName(dto.fullName());
        return user;
    }

    // UserProfile mapping
    public UserProfileResponseDTO toUserProfileResponseDto(UserProfile profile) {
        if (profile == null) return null;
        return new UserProfileResponseDTO(
                profile.getId(),
                profile.getNickname(),
                profile.getPhoneNumber(),
                profile.getBio(),
                profile.getAddress()
        );
    }

    public UserProfile toUserProfileEntity(UserProfileRequestDTO dto) {
        if (dto == null) return null;
        UserProfile profile = new UserProfile();
        profile.setNickname(dto.nickname());
        profile.setPhoneNumber(dto.phoneNumber());
        profile.setBio(dto.bio());
        profile.setAddress(dto.address());
        return profile;
    }


    // Event mapping
    public EventResponseDTO toEventResponseDto(Event event) {
        if (event == null) return null;
        return new EventResponseDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDateTime(),
                event.getLocation(),
                event.getStatus() != null ? event.getStatus().name() : null,
                event.getImageUrl(),
                event.getCategory(),
                event.getParticipants() != null ? (long) event.getParticipants().size() : 0L,
                toUserResponseDto(event.getCreatedBy()),
                event.getParticipants() != null ?
                        event.getParticipants().stream().map(this::toUserResponseDto).collect(Collectors.toSet()) :
                        Collections.emptySet()
        );
    }

    public Event toEventEntity(EventRequestDTO dto) {
        if (dto == null) return null;
        Event event = new Event();
        event.setTitle(dto.title());
        event.setDescription(dto.description());
        event.setDateTime(dto.dateTime());
        event.setLocation(dto.location());
        event.setStatus(EventStatus.fromString(dto.status()));
        event.setImageUrl(dto.imageUrl());
        event.setCategory(dto.category());
        return event;
    }
}
