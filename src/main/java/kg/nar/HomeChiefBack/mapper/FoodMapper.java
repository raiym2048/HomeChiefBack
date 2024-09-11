package kg.nar.HomeChiefBack.mapper;

import kg.nar.HomeChiefBack.dto.ObjectDto;
import kg.nar.HomeChiefBack.dto.comment.CommentResponse;
import kg.nar.HomeChiefBack.dto.food.FoodResponse;
import kg.nar.HomeChiefBack.entity.Cut;
import kg.nar.HomeChiefBack.entity.Food;
import kg.nar.HomeChiefBack.entity.FoodType;
import kg.nar.HomeChiefBack.entity.User;

import java.util.List;

public interface FoodMapper {
    List<FoodResponse> toDtoS(List<Food> all, User user);

    FoodResponse toDto(Food food, User user);

    List<CommentResponse> commentToDtoS(Cut cut);

    List<ObjectDto> toDtoStype(List<FoodType> all);
}
