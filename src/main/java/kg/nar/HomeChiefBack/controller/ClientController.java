package kg.nar.HomeChiefBack.controller;

import kg.nar.HomeChiefBack.dto.food.FoodResponse;
import kg.nar.HomeChiefBack.entity.Food;
import kg.nar.HomeChiefBack.repository.PostRepository;
import kg.nar.HomeChiefBack.service.ChiefService;
import kg.nar.HomeChiefBack.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class ClientController {
    private final PostRepository postRepository;
    private final FoodService foodService;

    @GetMapping("/foods")
    private List<FoodResponse> foodResponses(){
        return foodService.getAll();
    }


    @GetMapping("/client")
    public String client() {
        Food food = new Food();
        postRepository.save(food);
        return "Hello, client!";
    }


}
