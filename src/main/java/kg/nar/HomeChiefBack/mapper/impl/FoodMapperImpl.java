package kg.nar.HomeChiefBack.mapper.impl;

import kg.nar.HomeChiefBack.dto.food.FoodResponse;
import kg.nar.HomeChiefBack.entity.Food;
import kg.nar.HomeChiefBack.mapper.FoodMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FoodMapperImpl implements FoodMapper{
    @Override
    public List<FoodResponse> toDtoS(List<Food> all) {
        List<FoodResponse> foodResponses = new ArrayList<>();
        for (Food food : all) {
            foodResponses.add(toDto(food));
        }
        return foodResponses;
    }

    @Override
    public FoodResponse toDto(Food food) {
        FoodResponse foodResponse = new FoodResponse();
        foodResponse.setId(food.getId());
        foodResponse.setName(food.getName());
        foodResponse.setPrice(food.getPrice());
        foodResponse.setFoodType(food.getFoodType().getName());
        if (!food.getImages().isEmpty()) {
            foodResponse.setImage(food.getImages().get(0));
            food.getImages().remove(0);
            foodResponse.setAdditionalImage(food.getImages());
        }
        foodResponse.setDiscount(food.getDiscount());
        foodResponse.setChiefName(food.getChief().getFirstname());
        return foodResponse;
    }
}
