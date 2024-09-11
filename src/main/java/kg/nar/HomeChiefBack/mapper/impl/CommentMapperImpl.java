package kg.nar.HomeChiefBack.mapper.impl;

import kg.nar.HomeChiefBack.dto.comment.CommentResponse;
import kg.nar.HomeChiefBack.entity.Comments;
import kg.nar.HomeChiefBack.mapper.CommentMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommentMapperImpl implements CommentMapper {
    @Override
    public List<CommentResponse> toDtoS(List<Comments> comments) {
        List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comments comment : comments) {
            commentResponses.add(toDto(comment));
        }
        return commentResponses;
    }

    private CommentResponse toDto(Comments comment) {
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setId(comment.getId());
        commentResponse.setTime(comment.getTime());
        commentResponse.setUsername(comment.getUser().getUsername());
        commentResponse.setTitle(comment.getTitle());
        return commentResponse;
    }
}
