package se.lexicon.eventapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.eventapi.dto.UserRequestDTO;
import se.lexicon.eventapi.dto.UserResponseDTO;
import se.lexicon.eventapi.entity.User;
import se.lexicon.eventapi.exception.DataNotFoundException;
import se.lexicon.eventapi.mapper.EntityDtoMapper;
import se.lexicon.eventapi.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the UserService interface.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EntityDtoMapper mapper;

    public UserServiceImpl(UserRepository userRepository, EntityDtoMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public UserResponseDTO register(UserRequestDTO userRequestDto) {
        if (userRequestDto == null) throw new IllegalArgumentException("User request cannot be null.");

        // Check if user already exists by email
        Optional<User> existingUser = userRepository.findByEmail(userRequestDto.email());

        User user;
        if (existingUser.isPresent()) {
            // If exists, update the user information (full name)
            user = existingUser.get();
            user.setFullName(userRequestDto.fullName());
        } else {
            // If does not exist, create a new user
            user = mapper.toUserEntity(userRequestDto);
        }

        User savedUser = userRepository.save(user);
        return mapper.toUserResponseDto(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserResponseDTO> findById(Long id) {
        return userRepository.findById(id).map(mapper::toUserResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream()
                .map(mapper::toUserResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new DataNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }
}
