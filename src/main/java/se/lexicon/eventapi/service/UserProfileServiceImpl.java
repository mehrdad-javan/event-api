package se.lexicon.eventapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.eventapi.dto.UserProfileRequestDTO;
import se.lexicon.eventapi.dto.UserProfileResponseDTO;
import se.lexicon.eventapi.entity.UserProfile;
import se.lexicon.eventapi.exception.DataNotFoundException;
import se.lexicon.eventapi.mapper.EntityDtoMapper;
import se.lexicon.eventapi.repository.UserProfileRepository;

import java.util.Optional;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final EntityDtoMapper mapper;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository, EntityDtoMapper mapper) {
        this.userProfileRepository = userProfileRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public UserProfileResponseDTO update(Long id, UserProfileRequestDTO profileRequestDto) {
        if (id == null) throw new IllegalArgumentException("Profile ID cannot be null for update.");
        if (profileRequestDto == null) throw new IllegalArgumentException("Profile request cannot be null.");
        
        UserProfile profile = userProfileRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Profile not found with ID: " + id));
        
        profile.setNickname(profileRequestDto.nickname());
        profile.setPhoneNumber(profileRequestDto.phoneNumber());
        profile.setBio(profileRequestDto.bio());
        profile.setAddress(profileRequestDto.address());
        
        UserProfile updatedProfile = userProfileRepository.save(profile);
        return mapper.toUserProfileResponseDto(updatedProfile);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserProfileResponseDTO> findById(Long id) {
        return userProfileRepository.findById(id).map(mapper::toUserProfileResponseDto);
    }
}
