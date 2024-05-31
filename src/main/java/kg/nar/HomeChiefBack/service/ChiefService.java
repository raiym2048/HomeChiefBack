package kg.nar.HomeChiefBack.service;

import kg.nar.HomeChiefBack.dto.food.FoodAddRequest;

public interface ChiefService {
    void addFood(String token, FoodAddRequest foodAddRequest);
}
