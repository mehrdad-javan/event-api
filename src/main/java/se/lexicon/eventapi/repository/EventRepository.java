package se.lexicon.eventapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.eventapi.entity.Event;
import se.lexicon.eventapi.entity.EventStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByTitleContaining(String title);

    List<Event> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);

    List<Event> findByLocation(String location);

    List<Event> findByStatus(EventStatus status);
}
