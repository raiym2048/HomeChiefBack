package kg.nar.HomeChiefBack.repository.address;

import kg.nar.HomeChiefBack.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {

}
