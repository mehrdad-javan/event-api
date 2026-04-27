package se.lexicon.eventapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.eventapi.dto.EventParticipantRequestDTO;
import se.lexicon.eventapi.dto.EventRequestDTO;
import se.lexicon.eventapi.dto.EventResponseDTO;
import se.lexicon.eventapi.entity.Event;
import se.lexicon.eventapi.entity.EventStatus;
import se.lexicon.eventapi.entity.User;
import se.lexicon.eventapi.exception.DataNotFoundException;
import se.lexicon.eventapi.exception.DuplicateEntryException;
import se.lexicon.eventapi.mapper.EntityDtoMapper;
import se.lexicon.eventapi.repository.EventRepository;
import se.lexicon.eventapi.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the EventService interface.
 */
@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EntityDtoMapper mapper;

    public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository, EntityDtoMapper mapper) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public EventResponseDTO create(EventRequestDTO eventRequestDto) {
        if (eventRequestDto == null) throw new IllegalArgumentException("Event request cannot be null.");
        User creator = userRepository.findById(eventRequestDto.createdByUserId())
                .orElseThrow(() -> new DataNotFoundException("Creator user not found with ID: " + eventRequestDto.createdByUserId()));

        Event event = mapper.toEventEntity(eventRequestDto);
        event.setCreatedBy(creator);

        Event savedEvent = eventRepository.save(event);
        return mapper.toEventResponseDto(savedEvent);
    }

    @Override
    @Transactional(readOnly = true)
    public EventResponseDTO findById(Long id) {
       Event event = eventRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Event not found with ID: " + id));
       return mapper.toEventResponseDto(event);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventResponseDTO> findAll() {
        List<Event> events = eventRepository.findAll();
        return events.stream().map(mapper::toEventResponseDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventResponseDTO update(Long id, EventRequestDTO eventRequestDto) {
        if (id == null) throw new IllegalArgumentException("Event ID cannot be null for update.");
        if (eventRequestDto == null) throw new IllegalArgumentException("Event request cannot be null.");

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Event not found with ID: " + id));

        User creator = userRepository.findById(eventRequestDto.createdByUserId())
                .orElseThrow(() -> new DataNotFoundException("Creator user not found with ID: " + eventRequestDto.createdByUserId()));

        event.setTitle(eventRequestDto.title());
        event.setDescription(eventRequestDto.description());
        event.setDateTime(eventRequestDto.dateTime());
        event.setLocation(eventRequestDto.location());
        event.setStatus(EventStatus.fromString(eventRequestDto.status()));
        event.setImageUrl(eventRequestDto.imageUrl());
        event.setCategory(eventRequestDto.category());
        event.setCreatedBy(creator);

        Event savedEvent = eventRepository.save(event);
        return mapper.toEventResponseDto(savedEvent);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new DataNotFoundException("Event not found with ID: " + id);
        }
        eventRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventResponseDTO> findByTitle(String title) {
        return eventRepository.findByTitleContaining(title).stream()
                .map(mapper::toEventResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventResponseDTO> findByDateRange(LocalDateTime start, LocalDateTime end) {
        return eventRepository.findByDateTimeBetween(start, end).stream()
                .map(mapper::toEventResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventResponseDTO> findByLocation(String location) {
        return eventRepository.findByLocation(location).stream()
                .map(mapper::toEventResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventResponseDTO> findByStatus(String status) {
        return eventRepository.findByStatus(EventStatus.fromString(status)).stream()
                .map(mapper::toEventResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addParticipant(Long eventId, EventParticipantRequestDTO participantRequestDto) {
        if (participantRequestDto == null) throw new IllegalArgumentException("Participant request cannot be null.");

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new DataNotFoundException("Event not found with ID: " + eventId));
        User user = userRepository.findByEmail(participantRequestDto.email())
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(participantRequestDto.email());
                    newUser.setFullName(participantRequestDto.name());
                    return userRepository.save(newUser);
                });

        // Prevent duplicate registration to the same event
        if (event.getParticipants() != null && event.getParticipants().contains(user)) {
            throw new DuplicateEntryException("You are already registered for this event.");
        }

        event.addParticipant(user);
        eventRepository.save(event);
    }

    @Override
    @Transactional
    public void removeParticipant(Long eventId, Long userId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new DataNotFoundException("Event not found with ID: " + eventId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found with ID: " + userId));

        event.removeParticipant(user);
        eventRepository.save(event);
    }
}
