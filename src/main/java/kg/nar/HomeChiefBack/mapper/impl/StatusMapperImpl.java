package kg.nar.HomeChiefBack.mapper.impl;

import kg.nar.HomeChiefBack.dto.ObjectDto;
import kg.nar.HomeChiefBack.dto.RequestHistoryResponse;
import kg.nar.HomeChiefBack.entity.RequestHistory;
import kg.nar.HomeChiefBack.entity.RequestStatus;
import kg.nar.HomeChiefBack.mapper.StatusMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StatusMapperImpl implements StatusMapper {
    @Override
    public List<ObjectDto> toDtoStatuses(List<RequestStatus> all) {
        List<ObjectDto> dtos = new ArrayList<>();
        for (RequestStatus status : all) {
            dtos.add(toDtoStatus(status));
        }
        return dtos;
    }

    @Override
    public List<RequestHistoryResponse> toDtoHistories(List<RequestHistory> all) {
        List<RequestHistoryResponse> dtos = new ArrayList<>();
        for (RequestHistory history : all) {
            dtos.add(toDtoHistory(history));
        }
        return dtos;
    }

    private RequestHistoryResponse toDtoHistory(RequestHistory history) {
        RequestHistoryResponse response = new RequestHistoryResponse();
        response.setTime(history.getLocalDateTime());
        response.setComment(history.getComment());
        response.setRequestStatus(history.getRequestStatus()!=null? history.
                getRequestStatus().getStatus(): null);
        response.setModifiedBy(history.getModifiedBy());
        response.setChiefId(history.getChief().getId());
        response.setChiefName(history.getChief().getFirstname()+ " " + history.getChief().getLastname());
        return response;
    }

    private ObjectDto toDtoStatus(RequestStatus status) {
        ObjectDto dto = new ObjectDto();
        dto.setName(status.getStatus());
        return dto;
    }
}
