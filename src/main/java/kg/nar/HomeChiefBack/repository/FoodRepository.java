package kg.nar.HomeChiefBack.repository;

import kg.nar.HomeChiefBack.entity.Comments;
import kg.nar.HomeChiefBack.entity.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FoodRepository extends JpaRepository<Food, UUID>{
    @Query("SELECT COUNT(u) FROM User u JOIN u.favoriteFoods f WHERE f.id = :foodId")
    int countUsersWhoFavorited(@Param("foodId") UUID foodId);

    Page<Food> findAllByFoodType_Id(UUID foodTypeId, PageRequest pageRequest);

    @Query("SELECT f FROM Food f JOIN f.comments c WHERE c.id = :commentId")
    Optional<Food> findFoodByCommentId(@Param("commentId") UUID commentId);
}
