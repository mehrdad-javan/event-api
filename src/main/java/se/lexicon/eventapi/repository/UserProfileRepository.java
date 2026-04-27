package se.lexicon.eventapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.eventapi.entity.UserProfile;

import java.util.List;
import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findByNickname(String nickname);

    List<UserProfile> findByAddressContaining(String address);

}
