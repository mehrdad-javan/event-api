package se.lexicon.eventapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.lexicon.eventapi.dto.UserProfileRequestDTO;
import se.lexicon.eventapi.dto.UserProfileResponseDTO;
import se.lexicon.eventapi.entity.UserProfile;
import se.lexicon.eventapi.exception.DataNotFoundException;
import se.lexicon.eventapi.mapper.EntityDtoMapper;
import se.lexicon.eventapi.repository.UserProfileRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceImplTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private EntityDtoMapper mapper;

    @InjectMocks
    private UserProfileServiceImpl userProfileService;

    private UserProfileRequestDTO profileRequestDTO;
    private UserProfile profile;
    private UserProfileResponseDTO profileResponseDTO;

    @BeforeEach
    void setUp() {
        profileRequestDTO = new UserProfileRequestDTO("nick", "123456", "bio", "address");
        profile = new UserProfile();
        profile.setId(1L);
        profile.setNickname("nick");

        profileResponseDTO = new UserProfileResponseDTO(1L, "nick", "123456", "bio", "address");
    }

    @Test
    @DisplayName("update() should update profile when it exists")
    void update_success() {
        // Stubbing with findById: Intercepts call to findById with 1L as argument.
        when(userProfileRepository.findById(1L)).thenReturn(Optional.of(profile));
        // any(UserProfile.class): An Argument Matcher that tells Mockito to intercept 
        // a call to 'save' with ANY UserProfile object.
        when(userProfileRepository.save(any(UserProfile.class))).thenReturn(profile);
        when(mapper.toUserProfileResponseDto(profile)).thenReturn(profileResponseDTO);

        UserProfileResponseDTO result = userProfileService.update(1L, profileRequestDTO);

        assertThat(result).isNotNull();
        assertThat(result.nickname()).isEqualTo("nick");
        // verify(): Ensures that the save() method was actually called on the mock.
        verify(userProfileRepository, times(1)).save(any(UserProfile.class));
    }

    @Test
    @DisplayName("update() should throw DataNotFoundException when ID not found")
    void update_notFound() {
        when(userProfileRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userProfileService.update(1L, profileRequestDTO))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessageContaining("Profile not found with ID: 1");
    }
}
