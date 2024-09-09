package kg.nar.HomeChiefBack.mapper;

import kg.nar.HomeChiefBack.dto.address.AddressResponse;
import kg.nar.HomeChiefBack.entity.Address;

import java.util.List;

public interface AddressMapper {
    List<AddressResponse> toDtoS(List<Address> addresses);
}
