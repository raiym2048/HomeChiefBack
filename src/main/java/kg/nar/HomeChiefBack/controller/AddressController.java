package kg.nar.HomeChiefBack.controller;


import kg.nar.HomeChiefBack.dto.ObjectDto;
import kg.nar.HomeChiefBack.dto.address.AddressResponse;
import kg.nar.HomeChiefBack.dto.chief.AddressRequest;
import kg.nar.HomeChiefBack.entity.AddressType;
import kg.nar.HomeChiefBack.repository.address.AddressRepository;
import kg.nar.HomeChiefBack.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/address")
public class AddressController {
    private final AddressService addressService;
    @GetMapping("types")
    public List<AddressType> getAddressTypes() {
        return addressService.getAllTypes();
    }

    @PostMapping("add/type")
    public void addAddressType(@RequestBody ObjectDto addressType) {
        addressService.addType(addressType.getName());
    }
    @PostMapping("/addAddress")
    public void addAddress(@RequestBody AddressRequest addressRequest) {
        addressService.addAddress(addressRequest);
    }
    @GetMapping("/address/byType")
    public List<AddressResponse> getAddressesByType(@RequestParam String type) {
        return addressService.getAddressesByType(type);
    }

}
