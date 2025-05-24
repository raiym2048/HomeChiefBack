package kg.nar.HomeChiefBack.service.impl;

import kg.nar.HomeChiefBack.dto.ObjectDto;
import kg.nar.HomeChiefBack.dto.comment.CommentResponse;
import kg.nar.HomeChiefBack.dto.comment.ReviewRequest;
import kg.nar.HomeChiefBack.dto.food.FoodAddRequest;
import kg.nar.HomeChiefBack.dto.food.FoodResponse;
import kg.nar.HomeChiefBack.entity.*;
import kg.nar.HomeChiefBack.enums.Role;
import kg.nar.HomeChiefBack.exception.BadRequestException;
import kg.nar.HomeChiefBack.exception.NotFoundException;
import kg.nar.HomeChiefBack.mapper.FoodMapper;
import kg.nar.HomeChiefBack.repository.*;
import kg.nar.HomeChiefBack.service.AuthService;
import kg.nar.HomeChiefBack.service.FileService;
import kg.nar.HomeChiefBack.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
    private final FoodTypeRepository foodTypeRepository;
    private final FoodMapper foodMapper;
    private final FoodRepository foodRepository;
    private final AuthService authService;
    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final FileService fileService;

    @Override
    public FoodType getFoodType(String type) {
        Optional<FoodType> foodType = foodTypeRepository.findByName(type);
        if (foodType.isPresent())
            return foodType.get();
        else
            throw new BadRequestException("Food type not found");
    }

    @Override
    public FoodType getFoodTypeByName(String name) {
        Optional<FoodType> foodType = foodTypeRepository.findByName(name);
        if (foodType.isPresent())
            return foodType.get();
        else
            throw new BadRequestException("Food type not found");
    }

    @Override
    public List<FoodResponse> getAll(String token, PageRequest pageRequest) {
        User user = null;
        if (token != null && !token.isEmpty()) {
            user = authService.getUsernameFromToken(token);
        }

        return foodMapper.toDtoS(foodRepository.findAll(pageRequest).getContent(), user);
    }

    @Override
    public List<FoodResponse> getAll(String token, UUID foodTypeId, PageRequest pageRequest) {
        User user = null;
        if (token != null && !token.isEmpty()) {
            user = authService.getUsernameFromToken(token);
        }

        return foodMapper.toDtoS(foodRepository.findAllByFoodType_Id(foodTypeId, pageRequest).getContent(), user);    }



    @Override
    public void reviewFood(String token, ReviewRequest request) {
        User user = authService.getUsernameFromToken(token);
        Optional<Food> foodOptional = foodRepository.findById(request.getFoodId());
        if (foodOptional.isEmpty())
            throw new NotFoundException("food not found with id: "+ request.getFoodId(), HttpStatus.NOT_FOUND);

        Review review = new Review();
        review.setStar(review.getStar());
        review.setFood(foodOptional.get());
        review.setUser(user);
        review.setTitle(review.getTitle());
        reviewRepository.save(review);

        foodOptional.get().getReviews().add(review);
        foodRepository.save(foodOptional.get());
    }



    @Override
    public Boolean like(String token, UUID foodId) {
        User user = authService.getUsernameFromToken(token);
        Optional<Food> foodOptional = foodRepository.findById(foodId);
        if (foodOptional.isEmpty())
            throw new NotFoundException("food not found with id: "+ foodId, HttpStatus.NOT_FOUND);
        if (foodOptional.get().getLikedUsers().contains(user)){
            foodOptional.get().getLikedUsers().remove(user);
        }else {
            foodOptional.get().getLikedUsers().add(user);
        }
        foodRepository.save(foodOptional.get());
        return foodOptional.get().getLikedUsers().contains(user);
    }

    @Override
    public Boolean favorite(String token, UUID foodId) {
        User user = authService.getUsernameFromToken(token);

        Optional<Food> foodOptional = foodRepository.findById(foodId);
        if (foodOptional.isEmpty())
            throw new NotFoundException("food not found with id: "+ foodId, HttpStatus.NOT_FOUND);
        if (user.getFavoriteFoods().contains(foodOptional.get())){
            user.getFavoriteFoods().remove(foodOptional.get());
        }else {
            user.getFavoriteFoods().add(foodOptional.get());
        }
        userRepository.save(user);
        return user.getFavoriteFoods().contains(foodOptional.get());
    }

    @Override
    public FoodType getFoodTypeById(UUID foodTypeId) {
        Optional<FoodType> foodType = foodTypeRepository.findById(foodTypeId);
        if (foodType.isPresent())
            return foodType.get();
        else
            throw new BadRequestException("Food type not found");    }

    @Override
    public FoodResponse getById(String token, UUID foodId) {
        User user = null;
        if (token != null && !token.isEmpty()) {
            user = authService.getUsernameFromToken(token);
        }
        Optional<Food> foodOptional =
                foodRepository.findById(foodId);
        if (foodOptional.isEmpty())
            throw new NotFoundException("food not found with id: "+ foodId, HttpStatus.NOT_FOUND);
        return foodMapper.toDto(foodOptional.get(), user);       }

    @Override
    public void deleteFoodById(UUID foodId, String authorization) {
        User user =  authService.getUsernameFromToken(authorization);
        Optional<Food> foodOptional = foodRepository.findById(foodId);
        if (foodOptional.isEmpty())
            throw new NotFoundException("food not found with id: "+ foodId, HttpStatus.NOT_FOUND);
/*
        if (foodOptional.get().getChief().equals(user.getChief()) || user.getRole().equals(Role.ADMIN) || user.getRole().equals(Role.MANAGER))
*///todo
            foodRepository.deleteById(foodId);

    }
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
    public void updateFood(FoodAddRequest request, UUID foodId, String authorization) {
        User user = authService.getUsernameFromToken(authorization);
        Optional<Food> foodOptional = foodRepository.findById(foodId);
        if (foodOptional.isEmpty())
            throw new NotFoundException("товар не найден!", HttpStatus.NOT_FOUND);
        if (!user.getRole().equals(Role.CHIEF) || !foodOptional.get().getChief().equals(user.getChief())) {
            throw new BadRequestException("User is not a chief or this food created by another chief!");
        }
        foodOptional.get().setName(request.getName());
        foodOptional.get().setDescription(request.getDescription());
        foodOptional.get().setPrice(request.getPrice());
        foodOptional.get().setDiscount(request.getDiscount());
        Optional<FoodType> foodTypeOptional = foodTypeRepository.findById(request.getFoodTypeId());
        if (foodTypeOptional.isEmpty())
            throw new NotFoundException("тип товара не найден!", HttpStatus.NOT_FOUND);
        foodOptional.get().setFoodType(foodTypeOptional.get());
        System.out.println("the food tostring: "+foodOptional.get().toString());
        foodRepository.save(foodOptional.get());

    }

    private void createFood(Chief chief, FoodAddRequest foodAddRequest, UUID userId, List<MultipartFile> files) {
        Food food = new Food();
        food.setChief(chief);
        food.setName(foodAddRequest.getName());
        food.setPrice(foodAddRequest.getPrice());
        food.setDiscount(foodAddRequest.getDiscount());
        food.setFoodType(getFoodTypeById(foodAddRequest.getFoodTypeId()));
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
