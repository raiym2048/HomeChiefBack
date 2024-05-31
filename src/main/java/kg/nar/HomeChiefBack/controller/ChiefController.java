package kg.nar.HomeChiefBack.controller;

import kg.nar.HomeChiefBack.dto.food.FoodAddRequest;
import kg.nar.HomeChiefBack.service.ChiefService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chief")
public class ChiefController {
    private final ChiefService chiefService;
    @PostMapping("/food/add")
    public void addFood(@RequestHeader("Authorization") String token,
                        @RequestBody FoodAddRequest foodAddRequest) {
        chiefService.addFood(token, foodAddRequest);
    }
}
