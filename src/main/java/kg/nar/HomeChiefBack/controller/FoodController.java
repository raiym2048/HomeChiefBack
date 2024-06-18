package kg.nar.HomeChiefBack.controller;


import kg.nar.HomeChiefBack.dto.comment.CommentResponse;
import kg.nar.HomeChiefBack.service.FoodService;
import lombok.Getter;
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


    @PostMapping("/comment")
    public void comment(@RequestHeader("Authorization") String token, @PathVariable Long foodId, @RequestParam String commentTitle){
        foodService.comment(foodId, token, commentTitle);
    }

    @GetMapping("/comments")
    public List<CommentResponse> commentResponses(@PathVariable Long foodId){
        return foodService.getFoodComments(foodId);
    }
}
