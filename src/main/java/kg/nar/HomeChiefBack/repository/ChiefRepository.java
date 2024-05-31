package kg.nar.HomeChiefBack.repository;

import kg.nar.HomeChiefBack.entity.Chief;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChiefRepository extends JpaRepository<Chief, UUID>{
}
