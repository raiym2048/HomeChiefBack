package kg.nar.HomeChiefBack.mapper;

import kg.nar.HomeChiefBack.dto.comment.CommentResponse;
import kg.nar.HomeChiefBack.dto.food.FoodResponse;
import kg.nar.HomeChiefBack.entity.Food;
import kg.nar.HomeChiefBack.entity.FoodType;

import java.util.List;

public interface FoodMapper {
    List<FoodResponse> toDtoS(List<Food> all);

    FoodResponse toDto(Food food);

    List<CommentResponse> commentToDtoS(Food food);
}
