package kg.nar.HomeChiefBack.controller;


import kg.nar.HomeChiefBack.service.FoodService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
