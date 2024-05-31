package kg.nar.HomeChiefBack.service;

import kg.nar.HomeChiefBack.dto.food.FoodResponse;
import kg.nar.HomeChiefBack.entity.FoodType;

import java.util.List;

public interface FoodService {
    FoodType getFoodTypeById(Long id);

    List<FoodResponse> getAll();

    List<String> getTypes();
}
