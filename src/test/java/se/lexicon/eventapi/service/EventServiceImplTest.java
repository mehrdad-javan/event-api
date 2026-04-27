package se.lexicon.eventapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.lexicon.eventapi.dto.EventRequestDTO;
import se.lexicon.eventapi.dto.EventResponseDTO;
import se.lexicon.eventapi.dto.UserResponseDTO;
import se.lexicon.eventapi.entity.Event;
import se.lexicon.eventapi.entity.EventStatus;
import se.lexicon.eventapi.entity.User;
import se.lexicon.eventapi.exception.DataNotFoundException;
import se.lexicon.eventapi.mapper.EntityDtoMapper;
import se.lexicon.eventapi.repository.EventRepository;
import se.lexicon.eventapi.repository.UserRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EntityDtoMapper mapper;

    @InjectMocks
    private EventServiceImpl eventService;

    private User creator;
    private Event event;
    private EventRequestDTO eventRequestDTO;
    private EventResponseDTO eventResponseDTO;

    @BeforeEach
    void setUp() {
        creator = new User();
        creator.setId(1L);
        creator.setEmail("creator@test.com");

        event = new Event();
        event.setId(1L);
        event.setTitle("Test Event");
        event.setDateTime(LocalDateTime.now().plusDays(1));
        event.setStatus(EventStatus.PLANNED);
        event.setCreatedBy(creator);

        eventRequestDTO = new EventRequestDTO(
                "Test Event",
                "Description",
                LocalDateTime.now().plusDays(1),
                "Location",
                "PLANNED",
                1L,
                "https://example.com/image.jpg",
                "Technology"
        );

        UserResponseDTO creatorResponse = new UserResponseDTO(1L, "creator@test.com", "Creator", Instant.now());
        eventResponseDTO = new EventResponseDTO(
                1L,
                "Test Event",
                "Description",
                event.getDateTime(),
                "Location",
                "PLANNED",
                "https://example.com/image.jpg",
                "Technology",
                100L,
                creatorResponse,
                Collections.emptySet()
        );
    }

    @Test
    @DisplayName("update() should update event when event exists")
    void update_success() {
        // Arrange
        // when(...findById(1L)): Intercepts findById with exact value 1L.
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(userRepository.findById(1L)).thenReturn(Optional.of(creator));
        
        // when(...save(any(Event.class))): Intercepts save with ANY Event object.
        //   - any(Event.class): This argument matcher helps avoid needing the exact object.
        when(eventRepository.save(any(Event.class))).thenReturn(event);
        when(mapper.toEventResponseDto(event)).thenReturn(eventResponseDTO);

        // Act
        EventResponseDTO result = eventService.update(1L, eventRequestDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.title()).isEqualTo("Test Event");
        // verify(): Confirms the save method was called with ANY Event object.
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    @DisplayName("update() should throw DataNotFoundException when ID not found")
    void update_notFound() {
        // Arrange
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> eventService.update(1L, eventRequestDTO))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessageContaining("Event not found with ID: 1");
    }

    @Test
    @DisplayName("create() should throw DataNotFoundException when creator not found")
    void create_creatorNotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> eventService.create(eventRequestDTO))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessageContaining("Creator user not found with ID: 1");
    }
}