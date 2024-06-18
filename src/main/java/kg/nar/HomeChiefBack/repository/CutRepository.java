package kg.nar.HomeChiefBack.repository;

import kg.nar.HomeChiefBack.entity.Cut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CutRepository extends JpaRepository<Cut, Long> {
}
