package kg.nar.HomeChiefBack.service.impl;

import io.swagger.v3.oas.annotations.servers.Server;
import kg.nar.HomeChiefBack.dto.chief.ChiefInfoResponse;
import kg.nar.HomeChiefBack.entity.Address;
import kg.nar.HomeChiefBack.entity.Chief;
import kg.nar.HomeChiefBack.entity.User;
import kg.nar.HomeChiefBack.repository.ChiefRepository;
import kg.nar.HomeChiefBack.repository.UserRepository;
import kg.nar.HomeChiefBack.repository.address.AddressRepository;
import kg.nar.HomeChiefBack.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {
    private final ChiefRepository chiefRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Override
    public List<ChiefInfoResponse> requests() {
        List<Chief> chiefs = chiefRepository.findAllByActivated(false);
        List<ChiefInfoResponse> chiefInfoResponses = new ArrayList<>();
        for (Chief chief : chiefs) {
            User user = userRepository.findByChiefId(chief.getId()).get();
            ChiefInfoResponse chiefInfoResponse = new ChiefInfoResponse();
            chiefInfoResponse.setUserId(user.getId());
            chiefInfoResponse.setAddress(setResponse(chief.getAddress()));
            chiefInfoResponse.setFirstName(chief.getFirstname());
            chiefInfoResponse.setLastName(chief.getLastname());
            chiefInfoResponse.setPhone(user.getPhoneNumber());
            chiefInfoResponses.add(chiefInfoResponse);
        }
        return chiefInfoResponses;
    }
    private String setResponse(Address addresses) {
        StringBuilder response = new StringBuilder();
            List<String> values = new ArrayList<>();
            collectValues(addresses, values);
            Collections.reverse(values);  // Reverse the list to start from the root
            for (String value : values) {
                response.append(value);
            }

        return response.toString(); // todo
    }
    private void collectValues(Address address, List<String> values) {
        if (address != null) {
            values.add(address.getType().getValue()+": "+  address.getValue()+" ");
            if (address.getParentId() != null) {
                addressRepository.findById(address.getParentId()).ifPresent(parent -> collectValues(parent, values));
            }
        }
    }
}
