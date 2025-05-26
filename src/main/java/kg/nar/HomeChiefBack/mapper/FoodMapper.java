package kg.nar.HomeChiefBack.mapper;

import kg.nar.HomeChiefBack.dto.ObjectDto;
import kg.nar.HomeChiefBack.dto.chief.ChiefInfoResponse;
import kg.nar.HomeChiefBack.dto.comment.CommentResponse;
import kg.nar.HomeChiefBack.dto.food.FoodResponse;
import kg.nar.HomeChiefBack.entity.*;

import java.util.List;

public interface FoodMapper {
    List<FoodResponse> toDtoS(List<Food> all, User user);

    FoodResponse toDto(Food food, User user);

    List<CommentResponse> commentToDtoS(Cut cut);

    List<ObjectDto> toDtoStype(List<FoodType> all);

    ChiefInfoResponse toResponse(Chief chief);

    String getFormattedAddress(Address address);
}
