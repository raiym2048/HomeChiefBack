package kg.nar.HomeChiefBack.mapper;

import kg.nar.HomeChiefBack.dto.comment.CommentResponse;
import kg.nar.HomeChiefBack.entity.Comments;

import java.util.List;

public interface CommentMapper {
    List<CommentResponse> toDtoS(List<Comments> comments);
}
