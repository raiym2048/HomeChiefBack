package kg.nar.HomeChiefBack.repository;

import kg.nar.HomeChiefBack.entity.Comments;
import kg.nar.HomeChiefBack.entity.Cut;
import kg.nar.HomeChiefBack.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CutRepository extends JpaRepository<Cut, UUID> {
    @Query("SELECT COUNT(u) FROM User u JOIN u.favoriteCuts c WHERE c.id = :cutId")
    int countUsersWhoFavorited(@Param("cutId") UUID cutId);

    @Query("SELECT f FROM Cut f JOIN f.comments c WHERE c.id = :commentId")
    Optional<Cut> findCutByCommentId(@Param("commentId") UUID commentId);
}
