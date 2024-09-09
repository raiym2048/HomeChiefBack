package kg.nar.HomeChiefBack.service;

import kg.nar.HomeChiefBack.dto.address.AddressResponse;
import kg.nar.HomeChiefBack.dto.chief.AddressRequest;
import kg.nar.HomeChiefBack.entity.AddressType;

import java.util.List;

public interface AddressService {
    List<AddressType> getAllTypes();

    void addType(String addressType);

    void addAddress(AddressRequest addressRequest);

    List<AddressResponse> getAddressesByType(String type);
}
