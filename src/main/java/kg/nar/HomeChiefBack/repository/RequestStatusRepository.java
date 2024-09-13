package kg.nar.HomeChiefBack.repository;

import kg.nar.HomeChiefBack.entity.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RequestStatusRepository extends JpaRepository<RequestStatus, UUID> {

    Optional<RequestStatus> findByStatus(String accepted);
}
