package kg.nar.HomeChiefBack.repository;

import kg.nar.HomeChiefBack.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Long> {
}
