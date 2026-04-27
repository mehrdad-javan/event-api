package se.lexicon.eventapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"profile", "createdEvents"})
@EqualsAndHashCode(exclude = {"profile", "createdEvents"})
@Entity
@Table(name = "users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, updatable = false)
    private Instant createDate;

    @PrePersist
    void prePersist() {
        this.createDate = Instant.now();
    }

    @PreUpdate
    void preUpdate() {
    }

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserProfile profile;

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private List<Event> createdEvents = new ArrayList<>();


}
