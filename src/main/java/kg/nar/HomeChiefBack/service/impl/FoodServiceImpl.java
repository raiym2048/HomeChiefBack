package kg.nar.HomeChiefBack.service.impl;

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
}
