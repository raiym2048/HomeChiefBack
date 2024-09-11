package kg.nar.HomeChiefBack.repository;

import kg.nar.HomeChiefBack.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FoodRepository extends JpaRepository<Food, UUID>{
    @Query("SELECT COUNT(u) FROM User u JOIN u.favoriteFoods f WHERE f.id = :foodId")
    int countUsersWhoFavorited(@Param("foodId") UUID foodId);
}
