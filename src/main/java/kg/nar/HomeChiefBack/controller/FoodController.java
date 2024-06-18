package kg.nar.HomeChiefBack.controller;


import kg.nar.HomeChiefBack.dto.comment.CommentResponse;
import kg.nar.HomeChiefBack.dto.comment.ReviewRequest;
import kg.nar.HomeChiefBack.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/food")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FoodController {
    private final FoodService foodService;

    @GetMapping("/types")
    public List<String> foodTypes(){
        return foodService.getTypes();
    }


    @PostMapping("/comment/{cutId}")
    public void comment(@RequestHeader("Authorization") String token, @PathVariable Long cutId, @RequestParam String commentTitle){
        foodService.comment(cutId, token, commentTitle);
    }

    @GetMapping("/review/{foodId}")
    public List<CommentResponse> commentResponses(@PathVariable Long foodId){
        return foodService.getCutComments(foodId);
    }

    @PostMapping("/review")
    public void reviewFood(@RequestHeader("Authorization") String token, @RequestBody ReviewRequest request){
        foodService.reviewFood(token, request);
    }

}

