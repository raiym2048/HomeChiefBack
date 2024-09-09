package kg.nar.HomeChiefBack.repository;

import kg.nar.HomeChiefBack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByPhoneNumber(String phoneNumber);


    boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findByChiefId(UUID id);
}
