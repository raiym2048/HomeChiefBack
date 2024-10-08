package kg.nar.HomeChiefBack.repository;

import kg.nar.HomeChiefBack.entity.FoodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FoodTypeRepository extends JpaRepository<FoodType, UUID> {

    Optional<FoodType> findByName(String name);

    void deleteByName(String type);
}
