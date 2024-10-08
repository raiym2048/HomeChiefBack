package kg.nar.HomeChiefBack.service;

import kg.nar.HomeChiefBack.dto.ObjectDto;
import kg.nar.HomeChiefBack.dto.comment.CommentResponse;
import kg.nar.HomeChiefBack.dto.comment.ReviewRequest;
import kg.nar.HomeChiefBack.dto.food.FoodResponse;
import kg.nar.HomeChiefBack.entity.FoodType;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

public interface FoodService {
    FoodType getFoodType(String name);

    FoodType getFoodTypeByName(String name);

    List<FoodResponse> getAll(String token, PageRequest pageRequest);
    List<FoodResponse> getAll(String token, UUID foodTypeId, PageRequest pageRequest);

    List<ObjectDto> getTypes();



    void reviewFood(String token, ReviewRequest request);

    void addType(String type);

    void deleteType(String type);

    void refactor(String oldType, String newType);

    Boolean like(String token, UUID foodId);

    Boolean favorite(String token, UUID foodId);

    FoodType getFoodTypeById(UUID foodTypeId);

    FoodResponse getById(String authorization, UUID foodId);

    void deleteFoodById(UUID foodId, String authorization);
}
