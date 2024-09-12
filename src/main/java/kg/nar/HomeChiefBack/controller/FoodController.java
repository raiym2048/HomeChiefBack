package kg.nar.HomeChiefBack.controller;


import kg.nar.HomeChiefBack.dto.ObjectDto;
import kg.nar.HomeChiefBack.dto.comment.CommentResponse;
import kg.nar.HomeChiefBack.dto.comment.ReviewRequest;
import kg.nar.HomeChiefBack.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/food")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FoodController {
    private final FoodService foodService;

    @GetMapping("/types")
    public List<ObjectDto> foodTypes(){
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

    @PostMapping("/type/add")
    public void addType(@RequestParam String type){
        foodService.addType(type);
    }
    @DeleteMapping("/type/delete")
    public void deleteType(@RequestParam String type){
        foodService.deleteType(type);
    }
    @PutMapping("/type/refactor")
    public void refactorType(@RequestParam String oldType, @RequestParam String newType){
        foodService.refactor(oldType, newType);
    }
    @PostMapping("/like/{foodId}")
    public Boolean likeFood(@RequestHeader("Authorization") String token, @PathVariable UUID foodId){
        return foodService.like(token, foodId);
    }
    @PostMapping("/favorite/{foodId}")
    public Boolean favoriteFood(@RequestHeader("Authorization") String token, @PathVariable UUID foodId){
        return foodService.favorite(token, foodId);
    }
}

