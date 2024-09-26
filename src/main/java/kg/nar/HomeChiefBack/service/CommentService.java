package kg.nar.HomeChiefBack.service;

import kg.nar.HomeChiefBack.dto.comment.CommentResponse;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    void commentFood(String token, UUID foodId, String comment);

    List<CommentResponse> getFoodComments(UUID foodId);

    void delete(String token, UUID commentId);
}
