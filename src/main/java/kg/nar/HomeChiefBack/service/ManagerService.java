package kg.nar.HomeChiefBack.service;

import kg.nar.HomeChiefBack.dto.ObjectDto;
import kg.nar.HomeChiefBack.dto.RequestHistoryResponse;
import kg.nar.HomeChiefBack.dto.chief.ChiefInfoResponse;

import java.util.List;
import java.util.UUID;

public interface ManagerService {
    List<ChiefInfoResponse> requests(String activated);

    void accept(String token, UUID chiefId, String accepted, String comment);

    void addRequestStatus(String status);

    List<ObjectDto> requestStatuses();

    List<RequestHistoryResponse> requestHistory();
}
