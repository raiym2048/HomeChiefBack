package kg.nar.HomeChiefBack.repository;

import kg.nar.HomeChiefBack.entity.Bucket;
import kg.nar.HomeChiefBack.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BucketRepository extends JpaRepository<Bucket, Long>{
    List<Bucket> findByClientId(UUID clientId);

}
