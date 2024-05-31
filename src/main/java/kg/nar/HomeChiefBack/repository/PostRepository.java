package kg.nar.HomeChiefBack.repository;

import kg.nar.HomeChiefBack.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Food, Long> {
}
