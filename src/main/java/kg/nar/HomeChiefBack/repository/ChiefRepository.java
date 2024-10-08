package kg.nar.HomeChiefBack.repository;

import kg.nar.HomeChiefBack.entity.Chief;
import kg.nar.HomeChiefBack.entity.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChiefRepository extends JpaRepository<Chief, UUID>{
    List<Chief> findAllByActivated_Status(String activated);
}
