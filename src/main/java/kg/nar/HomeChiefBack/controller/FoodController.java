package kg.nar.HomeChiefBack.controller;


import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import kg.nar.HomeChiefBack.dto.ObjectDto;
import kg.nar.HomeChiefBack.dto.comment.CommentResponse;
import kg.nar.HomeChiefBack.dto.comment.ReviewRequest;
import kg.nar.HomeChiefBack.dto.food.FoodResponse;
import kg.nar.HomeChiefBack.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/food")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FoodController {
    private final FoodService foodService;
    @GetMapping("/foods")
    private List<FoodResponse> foodResponses(HttpServletRequest request,  @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        return foodService.getAll(request.getHeader("Authorization"), PageRequest.of(page, size));
    }

    @GetMapping("/byType/{foodTypeId}")
    private List<FoodResponse> foodResponsesByTypeId(HttpServletRequest request, @PathVariable UUID foodTypeId,  @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        return foodService.getAll(request.getHeader("Authorization"), foodTypeId, PageRequest.of(page, size));
    }
    @GetMapping("/{foodId}")
    private FoodResponse foodResponsesById(HttpServletRequest request, @PathVariable UUID foodId) {
        return foodService.getById(request.getHeader("Authorization"), foodId);
    }

    @DeleteMapping("/delete/{foodId}")
    public void deleteById(HttpServletRequest request, @PathVariable UUID foodId) {
        foodService.deleteFoodById(foodId, request.getHeader("Authorization"));
    }


    @GetMapping("/types")
    public List<ObjectDto> foodTypes(){
        return foodService.getTypes();
    }


    @PostMapping("/comment/{cutId}")
    public void comment(HttpServletRequest request, @PathVariable UUID cutId, @RequestParam String commentTitle){
        foodService.comment(cutId, request.getHeader("Authorization"), commentTitle);
    }

    @GetMapping("/review/{foodId}")
    public List<CommentResponse> commentResponses(@PathVariable UUID foodId){
        return foodService.getCutComments(foodId);
    }

    @PostMapping("/review")
    public void reviewFood(HttpServletRequest httpServletRequest, @RequestBody ReviewRequest request){
        foodService.reviewFood(httpServletRequest.getHeader("Authorization"), request);
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
    public Boolean likeFood(HttpServletRequest request, @PathVariable UUID foodId){
        return foodService.like(request.getHeader("Authorization"), foodId);
    }
    @PostMapping("/favorite/{foodId}")
    public Boolean favoriteFood(HttpServletRequest request, @PathVariable UUID foodId){
        return foodService.favorite(request.getHeader("Authorization"), foodId);
    }
}

