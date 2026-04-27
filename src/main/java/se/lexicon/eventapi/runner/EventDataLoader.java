package se.lexicon.eventapi.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.lexicon.eventapi.entity.Event;
import se.lexicon.eventapi.entity.EventStatus;
import se.lexicon.eventapi.entity.User;
import se.lexicon.eventapi.repository.EventRepository;
import se.lexicon.eventapi.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class EventDataLoader implements CommandLineRunner {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Autowired
    public EventDataLoader(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (eventRepository.count() == 0) {
            // 1. Ensure a default user exists to be the creator
            User admin = userRepository.findByEmail("admin@lexicon.se").orElseGet(() -> {
                User newUser = new User();
                newUser.setFullName("Admin User");
                newUser.setEmail("admin@lexicon.se");
                return userRepository.save(newUser);
            });

            // 2. Define the events to insert
            Event event1 = new Event();
            event1.setTitle("Tech Innovators Summit 2026");
            event1.setDateTime(LocalDateTime.of(2026, 3, 25, 9, 0));
            event1.setLocation("Silicon Valley Convention Center");
            event1.setCategory("Technology");
            event1.setImageUrl("https://images.unsplash.com/photo-1501281668745-f7f57925c3b4?auto=format&fit=crop&q=80&w=800");
            event1.setStatus(EventStatus.PLANNED);
            event1.setCreatedBy(admin);

            Event event2 = new Event();
            event2.setTitle("Global Marketing Expo");
            event2.setDateTime(LocalDateTime.of(2026, 4, 12, 10, 30));
            event2.setLocation("New York City Hall");
            event2.setCategory("Marketing");
            event2.setImageUrl("https://images.unsplash.com/photo-1505373877841-8d25f7d46678?auto=format&fit=crop&q=80&w=800");
            event2.setStatus(EventStatus.PLANNED);
            event2.setCreatedBy(admin);

            Event event3 = new Event();
            event3.setTitle("Sustainable Future Forum");
            event3.setDateTime(LocalDateTime.of(2026, 5, 5, 14, 0));
            event3.setLocation("London Eco Park");
            event3.setCategory("Environment");
            event3.setImageUrl("https://images.unsplash.com/photo-1475721027785-f74eccf877e2?auto=format&fit=crop&q=80&w=800");
            event3.setStatus(EventStatus.PLANNED);
            event3.setCreatedBy(admin);

            Event event4 = new Event();
            event4.setTitle("AI & Robotics Workshop");
            event4.setDateTime(LocalDateTime.of(2026, 6, 18, 11, 0));
            event4.setLocation("Tokyo Innovation Hub");
            event4.setCategory("Workshop");
            event4.setImageUrl("https://images.unsplash.com/photo-1485827404703-89b55fcc595e?auto=format&fit=crop&q=80&w=800");
            event4.setStatus(EventStatus.PLANNED);
            event4.setCreatedBy(admin);

            Event event5 = new Event();
            event5.setTitle("Annual Design Awards");
            event5.setDateTime(LocalDateTime.of(2026, 7, 22, 19, 0));
            event5.setLocation("Paris Design Center");
            event5.setCategory("Design");
            event5.setImageUrl("https://images.unsplash.com/photo-1511795409834-ef04bbd61622?auto=format&fit=crop&q=80&w=800");
            event5.setStatus(EventStatus.PLANNED);
            event5.setCreatedBy(admin);

            Event event6 = new Event();
            event6.setTitle("Startup Founders Networking");
            event6.setDateTime(LocalDateTime.of(2026, 8, 10, 18, 30));
            event6.setLocation("Berlin Co-working Space");
            event6.setCategory("Networking");
            event6.setImageUrl("https://images.unsplash.com/photo-1515187029135-18ee286d815b?auto=format&fit=crop&q=80&w=800");
            event6.setStatus(EventStatus.PLANNED);
            event6.setCreatedBy(admin);

            eventRepository.saveAll(Arrays.asList(event1, event2, event3, event4, event5, event6));
            System.out.println("✅ Data Loader: Inserted 6 demo events into the database.");
        } else {
            System.out.println("ℹ️ Data Loader: Events table is not empty, skipping data insertion.");
        }
    }
}
