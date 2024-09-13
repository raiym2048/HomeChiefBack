package kg.nar.HomeChiefBack.service.impl;

import kg.nar.HomeChiefBack.dto.comment.CommentResponse;
import kg.nar.HomeChiefBack.entity.Comments;
import kg.nar.HomeChiefBack.entity.Food;
import kg.nar.HomeChiefBack.entity.User;
import kg.nar.HomeChiefBack.enums.Role;
import kg.nar.HomeChiefBack.exception.BadRequestException;
import kg.nar.HomeChiefBack.exception.NotFoundException;
import kg.nar.HomeChiefBack.mapper.CommentMapper;
import kg.nar.HomeChiefBack.repository.CommentRepository;
import kg.nar.HomeChiefBack.repository.FoodRepository;
import kg.nar.HomeChiefBack.service.AuthService;
import kg.nar.HomeChiefBack.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final FoodRepository foodRepository;
    private final AuthService authService;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    @Override
    public void commentFood(String token, UUID foodId, String comment) {
        User user = authService.getUsernameFromToken(token);
        Optional<Food> food = foodRepository.findById(foodId);
        if (food.isEmpty()) {
            throw new BadRequestException("Invalid food");
        }
        Comments comments = new Comments();
        comments.setUser(user);
        comments.setTime(LocalDateTime.now());
        comments.setTitle(comment);
        commentRepository.save(comments);

        food.get().getComments().add(comments);
        foodRepository.save(food.get());
    }

    @Override
    public List<CommentResponse> getFoodComments(UUID foodId) {
        Optional<Food> food = foodRepository.findById(foodId);
        if (food.isEmpty()) {
            throw new BadRequestException("Invalid food");
        }
        return commentMapper.toDtoS(food.get().getComments());

    }

    @Override
    public void delete(String token, UUID foodId, UUID commentId) {
        User user = authService.getUsernameFromToken(token);
        Optional<Food> food = foodRepository.findById(foodId);
        if (food.isEmpty()) {
            throw new BadRequestException("Invalid food");
        }
        Optional<Comments> comment = commentRepository.findById(commentId);
        if (comment.isEmpty()) {
            throw new BadRequestException("Invalid comment");
        }
        if (!comment.get().getUser().equals(user) && !user.getChief().equals(food.get().getChief()) &&
         !user.getRole().equals(Role.ADMIN) && !user.getRole().equals(Role.MANAGER))
            throw new BadRequestException("комментарий могут удалять только те кто создавал публикацию или если its ur comment");
        food.get().getComments().remove(comment.get());
        foodRepository.save(food.get());
        commentRepository.deleteById(commentId);

    }
}