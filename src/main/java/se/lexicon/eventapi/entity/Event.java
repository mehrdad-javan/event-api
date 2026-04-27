package se.lexicon.eventapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"createdBy", "participants"})
@EqualsAndHashCode(exclude = {"createdBy", "participants"})
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    private String location;

    @Enumerated(EnumType.STRING)
    private EventStatus status;

    private String imageUrl;
    private String category;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by_user_id")
    private User createdBy;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "event_participants",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> participants = new HashSet<>();

    public void addParticipant(User user) {
        if (user == null) throw new IllegalArgumentException("User cannot be null");
        participants.add(user);
    }

    public void removeParticipant(User user) {
        if (user == null) throw new IllegalArgumentException("User cannot be null");
        participants.remove(user);
    }
}
