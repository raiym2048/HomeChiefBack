package kg.nar.HomeChiefBack.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import kg.nar.HomeChiefBack.dto.ObjectDto;
import kg.nar.HomeChiefBack.dto.comment.CommentResponse;
import kg.nar.HomeChiefBack.dto.comment.ReviewRequest;
import kg.nar.HomeChiefBack.dto.food.FoodAddRequest;
import kg.nar.HomeChiefBack.dto.food.FoodResponse;
import kg.nar.HomeChiefBack.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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




    @PostMapping("/review")
    public void reviewFood(HttpServletRequest httpServletRequest, @RequestBody ReviewRequest request){
        foodService.reviewFood(httpServletRequest.getHeader("Authorization"), request);
    }

    @PostMapping("/like/{foodId}")
    public Boolean likeFood(HttpServletRequest request, @PathVariable UUID foodId){
        return foodService.like(request.getHeader("Authorization"), foodId);
    }
    @PostMapping("/favorite/{foodId}")
    public Boolean favoriteFood(HttpServletRequest request, @PathVariable UUID foodId){
        return foodService.favorite(request.getHeader("Authorization"), foodId);
    }
    @PostMapping(value = "/food/add", consumes = "multipart/form-data")
    public ResponseEntity<?> addFood(
            HttpServletRequest request,
            @RequestPart("file") List<MultipartFile> files,
            @RequestPart("data") String foodDataString) throws JsonProcessingException {  // Note: Changed to String to debug
        if (files.size() > 5) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot upload more than 5 files");
        }
        ObjectMapper mapper = new ObjectMapper();
        FoodAddRequest foodAddRequest = mapper.readValue(foodDataString, FoodAddRequest.class);
        System.out.println(foodAddRequest.getName());  // Example of accessing the data

        foodService.addFood(request.getHeader("Authorization"),files, foodAddRequest);
        return ResponseEntity.ok("Food added");
    }

    @PutMapping("/update/{foodId}")
    public void updateFood(HttpServletRequest request, @PathVariable UUID foodId, FoodAddRequest foodAddRequest){
        foodService.updateFood(foodAddRequest, foodId, request.getHeader("Authorization"));
    }



}

