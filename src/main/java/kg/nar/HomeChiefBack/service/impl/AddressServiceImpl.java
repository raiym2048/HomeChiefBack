package kg.nar.HomeChiefBack.service.impl;

import kg.nar.HomeChiefBack.dto.address.AddressResponse;
import kg.nar.HomeChiefBack.dto.chief.AddressRequest;
import kg.nar.HomeChiefBack.entity.Address;
import kg.nar.HomeChiefBack.entity.AddressType;
import kg.nar.HomeChiefBack.exception.BadRequestException;
import kg.nar.HomeChiefBack.exception.NotFoundException;
import kg.nar.HomeChiefBack.mapper.AddressMapper;
import kg.nar.HomeChiefBack.mapper.impl.AddressMapperImpl;
import kg.nar.HomeChiefBack.repository.address.AddressRepository;
import kg.nar.HomeChiefBack.repository.address.AddressTypeRepository;
import kg.nar.HomeChiefBack.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final AddressTypeRepository addressTypeRepository;
    private final AddressMapper addressMapper;
    private final AddressMapperImpl addressMapperImpl;

    @Override
    public List<AddressType> getAllTypes() {
        return addressTypeRepository.findAll();
    }

    @Override
    public void addType(String addressType) {
       if (addressTypeRepository.findAllByValue(addressType).isEmpty()) {
           AddressType addressTypeEntity = new AddressType();
           addressTypeEntity.setValue(addressType);
           addressTypeRepository.save(addressTypeEntity);
       }
    }

    @Override
    public void addAddress(AddressRequest addressRequest) {
        Address address = new Address();
        address.setValue(addressRequest.getValue());
        Optional<AddressType> addressTypeOptional = addressTypeRepository.findByValue(addressRequest.getType());
        if (addressTypeOptional.isEmpty()) {
            throw new NotFoundException("no type found: "+ addressRequest.getType()+"!", HttpStatus.NOT_FOUND);
        }
        address.setType(addressTypeOptional.get());
        address.setParentId(addressRequest.getParentId());

        List<Address> addressOptional = addressRepository.
                findAllByValueAndType_Value(addressRequest.getValue(),
                addressRequest.getType());
        if (!addressOptional.isEmpty()) {
            throw new BadRequestException("Такой адрес с таким типом уже существует!");
        }

        addressRepository.save(address);
    }

    @Override
    public List<AddressResponse> getAddressesByType(String type) {
        Optional<AddressType> addressTypeOptional = addressTypeRepository.findByValue(type);
        if (addressTypeOptional.isEmpty()) {
            throw new NotFoundException("no type found: "+ type+"!", HttpStatus.NOT_FOUND);
        }
        List<Address> addresses = addressRepository.findAllByType_Value(type);
        return addressMapper.toDtoS(addresses);
    }
}
