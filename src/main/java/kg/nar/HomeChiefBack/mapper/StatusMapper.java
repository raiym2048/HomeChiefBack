package kg.nar.HomeChiefBack.mapper;

import kg.nar.HomeChiefBack.dto.ObjectDto;
import kg.nar.HomeChiefBack.dto.RequestHistoryResponse;
import kg.nar.HomeChiefBack.entity.RequestHistory;
import kg.nar.HomeChiefBack.entity.RequestStatus;

import java.util.List;

public interface StatusMapper {
    List<ObjectDto> toDtoStatuses(List<RequestStatus> all);

    List<RequestHistoryResponse> toDtoHistories(List<RequestHistory> all);
}
