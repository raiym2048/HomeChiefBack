package kg.nar.HomeChiefBack.service;

import kg.nar.HomeChiefBack.dto.ObjectDto;
import kg.nar.HomeChiefBack.dto.comment.CommentResponse;
import kg.nar.HomeChiefBack.dto.comment.ReviewRequest;
import kg.nar.HomeChiefBack.dto.food.FoodAddRequest;
import kg.nar.HomeChiefBack.dto.food.FoodResponse;
import kg.nar.HomeChiefBack.entity.FoodType;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface FoodService {
    FoodType getFoodType(String name);

    FoodType getFoodTypeByName(String name);

    List<FoodResponse> getAll(String token, PageRequest pageRequest);
    List<FoodResponse> getAll(String token, UUID foodTypeId, PageRequest pageRequest);

    void reviewFood(String token, ReviewRequest request);


    Boolean like(String token, UUID foodId);

    Boolean favorite(String token, UUID foodId);

    FoodType getFoodTypeById(UUID foodTypeId);

    FoodResponse getById(String authorization, UUID foodId);

    void deleteFoodById(UUID foodId, String authorization);

    void addFood(String authorization, List<MultipartFile> files, FoodAddRequest foodAddRequest);

    void updateFood(FoodAddRequest foodAddRequest, UUID foodId, String authorization);

}
