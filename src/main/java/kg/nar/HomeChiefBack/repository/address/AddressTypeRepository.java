package kg.nar.HomeChiefBack.repository.address;

import kg.nar.HomeChiefBack.entity.AddressType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressTypeRepository extends JpaRepository<AddressType, UUID> {
    List<AddressType> findAllByValue(String type);
    Optional<AddressType> findByValue(String type);
}
