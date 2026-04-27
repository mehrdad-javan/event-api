package se.lexicon.eventapi.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "user")
@EqualsAndHashCode(exclude = "user")
@Entity
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(length = 100)
    private String nickname;

    @Column(length = 20)
    private String phoneNumber;

    @Column(length = 500)
    private String bio;

    @Column(length = 255)
    private String address;

    @Lob
    @Column(length = 1000000, name = "profile_image")
    private byte[] profileImage;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    void prePersist() {
    }

    @PreUpdate
    void preUpdate() {
    }
}
