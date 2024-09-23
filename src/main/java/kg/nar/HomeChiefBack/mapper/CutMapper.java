package kg.nar.HomeChiefBack.mapper;

import kg.nar.HomeChiefBack.dto.cut.CutResponse;
import kg.nar.HomeChiefBack.entity.Cut;
import kg.nar.HomeChiefBack.entity.User;

import java.util.List;

public interface CutMapper {
    List<CutResponse> toDtoS(List<Cut> all, User user);

    CutResponse toDto(Cut cut, User user);
}
