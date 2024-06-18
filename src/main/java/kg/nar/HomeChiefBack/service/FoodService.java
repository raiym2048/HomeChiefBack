package kg.nar.HomeChiefBack.service;

import kg.nar.HomeChiefBack.dto.comment.CommentResponse;
import kg.nar.HomeChiefBack.dto.food.FoodResponse;
import kg.nar.HomeChiefBack.entity.FoodType;

import java.util.List;

public interface FoodService {
    FoodType getFoodTypeById(Long id);

    List<FoodResponse> getAll();

    List<String> getTypes();

    void comment(Long foodId, String token, String commentTitle);

    List<CommentResponse> getFoodComments(Long foodId);
}
