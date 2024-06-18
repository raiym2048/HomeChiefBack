package kg.nar.HomeChiefBack.service;

import com.amazonaws.services.dynamodbv2.xspec.L;
import kg.nar.HomeChiefBack.dto.comment.CommentResponse;
import kg.nar.HomeChiefBack.dto.comment.ReviewRequest;
import kg.nar.HomeChiefBack.dto.food.FoodResponse;
import kg.nar.HomeChiefBack.entity.FoodType;

import java.util.List;

public interface FoodService {
    FoodType getFoodTypeById(Long id);

    List<FoodResponse> getAll();

    List<String> getTypes();

    void comment(Long cutId, String token, String commentTitle);

    List<CommentResponse> getCutComments(Long foodId);

    void reviewFood(String token, ReviewRequest request);
}
