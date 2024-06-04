package kg.nar.HomeChiefBack.controller;

import kg.nar.HomeChiefBack.dto.bucket.BucketResponse;
import kg.nar.HomeChiefBack.dto.food.FoodResponse;
import kg.nar.HomeChiefBack.entity.Food;
import kg.nar.HomeChiefBack.repository.PostRepository;
import kg.nar.HomeChiefBack.service.ChiefService;
import kg.nar.HomeChiefBack.service.ClientService;
import kg.nar.HomeChiefBack.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/client")
public class ClientController {
    private final PostRepository postRepository;
    private final FoodService foodService;
    private final ClientService clientService;

    @GetMapping("/foods")
    private List<FoodResponse> foodResponses(){
        return foodService.getAll();
    }

    @PostMapping("/private/add")
    private void addFoodToBucket(@RequestHeader("Authorization") String token,
                                 @RequestParam Long foodId, @RequestParam int count){
        clientService.addFoodToBucket(foodId, count, token);

    }

    @GetMapping("/private/bucket")
    private List<BucketResponse> getBucket(@RequestHeader("Authorization") String token){
        return clientService.getBucket(token);
    }





}
