package kg.nar.HomeChiefBack.controller;


import jakarta.servlet.http.HttpServletRequest;
import kg.nar.HomeChiefBack.dto.comment.CommentResponse;
import kg.nar.HomeChiefBack.repository.CommentRepository;
import kg.nar.HomeChiefBack.service.CommentService;
import kg.nar.HomeChiefBack.service.CutService;
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
    private final CutService cutService;

    @PostMapping("/add/{foodId}")
    public void commentAdd(HttpServletRequest request,
                           @PathVariable("foodId") UUID foodId, @RequestParam("comment") String comment) {
        commentService.commentFood(request.getHeader("Authorization"), foodId, comment);
    }
    @PostMapping("/add/{cutId}")
    public void comment(HttpServletRequest request, @PathVariable UUID cutId, @RequestParam String commentTitle){
        cutService.comment(cutId, request.getHeader("Authorization"), commentTitle);
    }

    @GetMapping("/food/{foodId}")
    public List<CommentResponse> getComments(@PathVariable UUID foodId) {
        return commentService.getFoodComments(foodId);
    }
    @DeleteMapping("/{commentId}")
    public void deleteComment(HttpServletRequest request, @PathVariable UUID commentId) {
        commentService.delete(request.getHeader("Authorization"), commentId);
    }


    @GetMapping("/comments/{cutId}")
    public List<CommentResponse> commentResponsesCut(@PathVariable UUID cutId){
        return cutService.getCutComments(cutId);
    }
    @GetMapping("/comments/{foodId}")
    public List<CommentResponse> commentResponsesFood(@PathVariable UUID foodId){
        return cutService.getFoodComments(foodId);
    }



}
