package kg.nar.HomeChiefBack.service.impl;

import io.swagger.v3.oas.annotations.servers.Server;
import kg.nar.HomeChiefBack.dto.ObjectDto;
import kg.nar.HomeChiefBack.dto.RequestHistoryResponse;
import kg.nar.HomeChiefBack.dto.chief.ChiefInfoResponse;
import kg.nar.HomeChiefBack.entity.*;
import kg.nar.HomeChiefBack.exception.NotFoundException;
import kg.nar.HomeChiefBack.mapper.StatusMapper;
import kg.nar.HomeChiefBack.repository.ChiefRepository;
import kg.nar.HomeChiefBack.repository.RequestHistoryRepository;
import kg.nar.HomeChiefBack.repository.RequestStatusRepository;
import kg.nar.HomeChiefBack.repository.UserRepository;
import kg.nar.HomeChiefBack.repository.address.AddressRepository;
import kg.nar.HomeChiefBack.service.AuthService;
import kg.nar.HomeChiefBack.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springdoc.webmvc.core.service.RequestService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {
    private final ChiefRepository chiefRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final RequestService requestService;
    private final RequestHistoryRepository requestHistoryRepository;
    private final RequestStatusRepository requestStatusRepository;
    private final StatusMapper statusMapper;

    @Override
    public List<ChiefInfoResponse> requests(String activated) {
        List<Chief> chiefs = chiefRepository.findAllByActivated_Status(activated);
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

    @Override
    public void accept(String token, UUID chiefId, String accepted, String comment) {
        User manager = authService.getUsernameFromToken(token);
        Optional<Chief> chiefOptional = chiefRepository.findById(chiefId);
        if (chiefOptional.isEmpty())
            throw new NotFoundException("повар не наден с такой айди", HttpStatus.NOT_FOUND);
        Optional<RequestStatus> requestStatusOptional = requestStatusRepository.findByStatus(accepted);
        if (requestStatusOptional.isEmpty())
            throw new NotFoundException("повар не наден с такой айди", HttpStatus.NOT_FOUND);
        chiefOptional.get().setActivated(requestStatusOptional.get());
        createRequestHistory(manager, chiefOptional.get(), requestStatusOptional.get(), comment);


    }

    private void createRequestHistory(User user, Chief chief, RequestStatus requestStatus, String comment) {
        RequestHistory requestHistory = new RequestHistory();
        requestHistory.setRequestStatus(requestStatus);
        requestHistory.setComment(comment);
        requestHistory.setChief(chief);
        requestHistory.setModifiedBy(user.getUsername());
        requestHistory.setLocalDateTime(LocalDateTime.now());
        requestHistoryRepository.save(requestHistory);
    }

    @Override
    public void addRequestStatus(String status) {
        if (requestStatusRepository.findByStatus(status).isEmpty()){
            RequestStatus requestStatus = new RequestStatus();
            requestStatus.setStatus(status);
            requestStatusRepository.save(requestStatus);
        }
    }

    @Override
    public List<ObjectDto> requestStatuses() {
        return statusMapper.toDtoStatuses(requestStatusRepository.findAll());
    }

    @Override
    public List<RequestHistoryResponse> requestHistory() {
        return statusMapper.toDtoHistories(requestHistoryRepository.findAll());
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
