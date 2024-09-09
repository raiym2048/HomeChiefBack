package kg.nar.HomeChiefBack.mapper.impl;

import kg.nar.HomeChiefBack.dto.address.AddressResponse;
import kg.nar.HomeChiefBack.entity.Address;
import kg.nar.HomeChiefBack.mapper.AddressMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AddressMapperImpl implements AddressMapper {
    @Override
    public List<AddressResponse> toDtoS(List<Address> addresses) {
        List<AddressResponse> addressResponses = new ArrayList<>();
        for (Address address : addresses) {
            addressResponses.add(toDto(address));
        }
        return addressResponses;
    }

    private AddressResponse toDto(Address address) {
        AddressResponse addressResponse = new AddressResponse();
        addressResponse.setAddressUid(address.getId());
        addressResponse.setParentUid(address.getParentId());
        addressResponse.setValue(address.getValue());
        return addressResponse;
    }
}
