package kg.nar.HomeChiefBack.controller;


import kg.nar.HomeChiefBack.dto.comment.CommentResponse;
import kg.nar.HomeChiefBack.repository.CommentRepository;
import kg.nar.HomeChiefBack.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/add/{foodId}")
    public void commentAdd(@RequestHeader("Authorization") String token,
                           @PathVariable("foodId") UUID foodId, @RequestParam("comment") String comment) {
        commentService.commentFood(token, foodId, comment);
    }
    @GetMapping("/food/{foodId}")
    public List<CommentResponse> getComments(@PathVariable UUID foodId) {
        return commentService.getFoodComments(foodId);
    }
    @DeleteMapping("/food/{foodId}/{commentId}")
    public void deleteComment(@RequestHeader("Authorization") String token,
                              @PathVariable UUID foodId, @PathVariable UUID commentId) {
        commentService.delete(token, foodId, commentId);
    }
}
