package kg.nar.HomeChiefBack.service.impl;

import kg.nar.HomeChiefBack.dto.food.FoodAddRequest;
import kg.nar.HomeChiefBack.entity.Chief;
import kg.nar.HomeChiefBack.entity.Food;
import kg.nar.HomeChiefBack.entity.User;
import kg.nar.HomeChiefBack.enums.Role;
import kg.nar.HomeChiefBack.exception.BadRequestException;
import kg.nar.HomeChiefBack.repository.FoodRepository;
import kg.nar.HomeChiefBack.repository.FoodTypeRepository;
import kg.nar.HomeChiefBack.service.AuthService;
import kg.nar.HomeChiefBack.service.ChiefService;
import kg.nar.HomeChiefBack.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChiefServiceImpl implements ChiefService {
    private final AuthService authService;
    private final FoodService foodService;
    private final FoodRepository foodRepository;

    @Override
    public void addFood(String token, FoodAddRequest foodAddRequest) {
        Optional.ofNullable(token).orElseThrow(() -> new BadRequestException("Token is required"));
        Optional.ofNullable(foodAddRequest).orElseThrow(() -> new BadRequestException("FoodAddRequest is required"));
        User user = authService.getUsernameFromToken(token);
        if (user.getRole().equals(Role.CHIEF)) {
            Chief chief = user.getChief();
            createFood(chief, foodAddRequest);
        }
        else throw new BadRequestException("User is not a chief");
    }

    private void createFood(Chief chief, FoodAddRequest foodAddRequest) {
        Food food = new Food();
        food.setChief(chief);
        food.setName(foodAddRequest.getName());
        food.setPrice(foodAddRequest.getPrice());
        food.setDiscount(foodAddRequest.getDiscount());
        food.setFoodType(foodService.getFoodTypeById(foodAddRequest.getFoodTypeId()));
        food.setImage(foodAddRequest.getImage());
        food.setDescription(foodAddRequest.getDescription());
        foodRepository.save(food);

    }
}
