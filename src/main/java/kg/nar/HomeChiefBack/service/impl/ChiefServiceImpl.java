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
import kg.nar.HomeChiefBack.service.FileService;
import kg.nar.HomeChiefBack.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChiefServiceImpl implements ChiefService {
    private final AuthService authService;
    private final FoodService foodService;
    private final FoodRepository foodRepository;
    private final FileService fileService;

    @Override
    public void addFood(String token, List<MultipartFile> files, FoodAddRequest foodAddRequest) {
        Optional.ofNullable(token).orElseThrow(() -> new BadRequestException("Token is required"));
        Optional.ofNullable(foodAddRequest).orElseThrow(() -> new BadRequestException("FoodAddRequest is required"));
        User user = authService.getUsernameFromToken(token);
        if (user.getRole().equals(Role.CHIEF)) {
            Chief chief = user.getChief();
            createFood(chief, foodAddRequest, user.getId(), files);
        }
        else throw new BadRequestException("User is not a chief");
    }

    @Override
    public Object getFiles(String token) throws IOException {
        User user = authService.getUsernameFromToken(token);
        return fileService.listFiles(user.getId());

    }

    private void createFood(Chief chief, FoodAddRequest foodAddRequest, UUID userId, List<MultipartFile> files) {
        Food food = new Food();
        food.setChief(chief);
        food.setName(foodAddRequest.getName());
        food.setPrice(foodAddRequest.getPrice());
        food.setDiscount(foodAddRequest.getDiscount());
        food.setFoodType(foodService.getFoodTypeById(foodAddRequest.getFoodTypeId()));
        food.setImages(uploadFoodsImages(files, userId));
        food.setDescription(foodAddRequest.getDescription());
        foodRepository.save(food);

    }

    private List<String> uploadFoodsImages(List<MultipartFile> files, UUID userId) {
        List<String> images = files.stream().map(file -> {
            try {
                return fileService.uploadFile(file, userId);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).toList();
        return images;
    }
}
