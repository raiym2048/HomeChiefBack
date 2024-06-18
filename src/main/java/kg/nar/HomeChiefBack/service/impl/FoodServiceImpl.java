package kg.nar.HomeChiefBack.service.impl;

import kg.nar.HomeChiefBack.dto.comment.CommentResponse;
import kg.nar.HomeChiefBack.dto.food.FoodResponse;
import kg.nar.HomeChiefBack.entity.Comments;
import kg.nar.HomeChiefBack.entity.Food;
import kg.nar.HomeChiefBack.entity.FoodType;
import kg.nar.HomeChiefBack.entity.User;
import kg.nar.HomeChiefBack.exception.BadRequestException;
import kg.nar.HomeChiefBack.exception.NotFoundException;
import kg.nar.HomeChiefBack.mapper.FoodMapper;
import kg.nar.HomeChiefBack.repository.CommentRepository;
import kg.nar.HomeChiefBack.repository.FoodRepository;
import kg.nar.HomeChiefBack.repository.FoodTypeRepository;
import kg.nar.HomeChiefBack.service.AuthService;
import kg.nar.HomeChiefBack.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
    private final FoodTypeRepository foodTypeRepository;
    private final FoodMapper foodMapper;
    private final FoodRepository foodRepository;
    private final AuthService authService;
    private final CommentRepository commentRepository;

    @Override
    public FoodType getFoodTypeById(Long id) {
        Optional<FoodType> foodType = foodTypeRepository.findById(id);
        if (foodType.isPresent())
            return foodType.get();
        else
            throw new BadRequestException("Food type not found");
    }

    @Override
    public List<FoodResponse> getAll() {
        return foodMapper.toDtoS(foodRepository.findAll());
    }

    @Override
    public List<String> getTypes() {
        return foodTypeRepository.findAll().stream().map(FoodType::getName).toList();
    }

    @Override
    public void comment(Long foodId, String token, String title) {
        User user = authService.getUsernameFromToken(token);
        Optional<Food> food = foodRepository.findById(foodId);
        if (food.isEmpty())
            throw new NotFoundException("not found food with id: "+ foodId, HttpStatus.NOT_FOUND);

        Comments comment = new Comments();
        comment.setFood(food.get());
        comment.setUser(user);
        comment.setTime(LocalDateTime.now());
        comment.setTitle(title);
        commentRepository.save(comment);

        food.get().getComments().add(comment);

        foodRepository.save(food.get());

    }

    @Override
    public List<CommentResponse> getFoodComments(Long foodId) {
        Optional<Food> foodOptional = foodRepository.findById(foodId);
        if (foodOptional.isEmpty())
            throw new NotFoundException("food not found with id: "+ foodId, HttpStatus.NOT_FOUND);
        return foodMapper.commentToDtoS(foodOptional.get());
    }
}
