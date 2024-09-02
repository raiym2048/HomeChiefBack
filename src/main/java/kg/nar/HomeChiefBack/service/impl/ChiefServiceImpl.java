package kg.nar.HomeChiefBack.service.impl;

import kg.nar.HomeChiefBack.dto.food.FoodAddRequest;
import kg.nar.HomeChiefBack.entity.Chief;
import kg.nar.HomeChiefBack.entity.Food;
import kg.nar.HomeChiefBack.entity.User;
import kg.nar.HomeChiefBack.enums.Role;
import kg.nar.HomeChiefBack.exception.BadRequestException;
import kg.nar.HomeChiefBack.exception.NotFoundException;
import kg.nar.HomeChiefBack.repository.FoodRepository;
import kg.nar.HomeChiefBack.repository.FoodTypeRepository;
import kg.nar.HomeChiefBack.repository.UserRepository;
import kg.nar.HomeChiefBack.service.AuthService;
import kg.nar.HomeChiefBack.service.ChiefService;
import kg.nar.HomeChiefBack.service.FileService;
import kg.nar.HomeChiefBack.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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
    private final UserRepository userRepository;

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

    @Override
    public Chief chiefGetInfo(UUID userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty())
            throw new NotFoundException("user not found with this id: "+ userId, HttpStatus.NOT_FOUND);
        if (!userOptional.get().getRole().equals(Role.CHIEF)){
            throw new BadRequestException("the user is not chief!");
        }
        return userOptional.get().getChief();

    }

    private void createFood(Chief chief, FoodAddRequest foodAddRequest, UUID userId, List<MultipartFile> files) {
        Food food = new Food();
        food.setChief(chief);
        food.setName(foodAddRequest.getName());
        food.setPrice(foodAddRequest.getPrice());
        food.setDiscount(foodAddRequest.getDiscount());
        food.setFoodType(foodService.getFoodTypeByName(foodAddRequest.getFoodType()));
        food.setImages(uploadFoodsImages(files, userId));
        food.setDescription(foodAddRequest.getDescription());
        foodRepository.save(food);

    }

    private List<String> uploadFoodsImages(List<MultipartFile> files, UUID userId) {
        List<String> images = new ArrayList<>();
        int i = 0;
        for (MultipartFile file : files) {
            try {
                images.add(fileService.uploadFile(file, userId, i));
                i++;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        return images;
    }
}
