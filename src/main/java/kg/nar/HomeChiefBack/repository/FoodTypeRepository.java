package kg.nar.HomeChiefBack.repository;

import kg.nar.HomeChiefBack.entity.FoodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodTypeRepository extends JpaRepository<FoodType, Long> {
}
