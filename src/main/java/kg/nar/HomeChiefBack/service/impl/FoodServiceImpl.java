package kg.nar.HomeChiefBack.service.impl;

import kg.nar.HomeChiefBack.dto.ObjectDto;
import kg.nar.HomeChiefBack.dto.comment.CommentResponse;
import kg.nar.HomeChiefBack.dto.comment.ReviewRequest;
import kg.nar.HomeChiefBack.dto.food.FoodResponse;
import kg.nar.HomeChiefBack.entity.*;
import kg.nar.HomeChiefBack.exception.BadRequestException;
import kg.nar.HomeChiefBack.exception.NotFoundException;
import kg.nar.HomeChiefBack.mapper.FoodMapper;
import kg.nar.HomeChiefBack.repository.*;
import kg.nar.HomeChiefBack.service.AuthService;
import kg.nar.HomeChiefBack.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private final CutRepository cutRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

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
    public List<FoodResponse> getAll(String token) {
        User user = null;
        if (token != null && !token.isEmpty()) {
            user = authService.getUsernameFromToken(token);
        }

        return foodMapper.toDtoS(foodRepository.findAll(), user);
    }

    @Override
    public List<ObjectDto> getTypes() {
        return foodMapper.toDtoStype(foodTypeRepository.findAll());
    }

    @Override
    public void comment(Long cutId, String token, String title) {
        User user = authService.getUsernameFromToken(token);
        Optional<Cut> cut = cutRepository.findById(cutId);
        if (cut.isEmpty())
            throw new NotFoundException("not found cut with id: "+ cutId, HttpStatus.NOT_FOUND);

        Comments comment = new Comments();
        comment.setCut(cut.get());
        comment.setUser(user);
        comment.setTime(LocalDateTime.now());
        comment.setTitle(title);
        commentRepository.save(comment);

        cut.get().getComments().add(comment);

        cutRepository.save(cut.get());

    }

    @Override
    public List<CommentResponse> getCutComments(Long cutId) {
        Optional<Cut> cutOptional = cutRepository.findById(cutId);
        if (cutOptional.isEmpty())
            throw new NotFoundException("cut not found with id: "+ cutId, HttpStatus.NOT_FOUND);
        return foodMapper.commentToDtoS(cutOptional.get());
    }

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
    public void addType(String type) {
        Optional<FoodType> foodType = foodTypeRepository.findByName(type);
        if (foodType.isEmpty()){
            FoodType newFoodType = new FoodType();
            newFoodType.setName(type);
            foodTypeRepository.save(newFoodType);
        }

    }

    @Override
    public void deleteType(String type) {
        foodTypeRepository.deleteByName(type);
    }

    @Override
    public void refactor(String oldType, String newType) {
        Optional<FoodType> foodType = foodTypeRepository.findByName(oldType);
        if (foodType.isEmpty())
            throw new BadRequestException("такой тип не существует!: "+ oldType);
        foodType.get().setName(newType);
        foodTypeRepository.save(foodType.get());

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
}
