package kg.nar.HomeChiefBack.controller;

import kg.nar.HomeChiefBack.dto.ObjectDto;
import kg.nar.HomeChiefBack.service.FoodTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/food-type")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FoodTypeController {
    private final FoodTypeService foodTypeService;
    @GetMapping("/types")
    public List<ObjectDto> foodTypes(){
        return foodTypeService.getTypes();
    }


    @PostMapping("/type/add")
    public void addType(@RequestParam String type){
        foodTypeService.addType(type);
    }
    @DeleteMapping("/type/delete")
    public void deleteType(@RequestParam String type){
        foodTypeService.deleteType(type);
    }
    @PutMapping("/type/refactor")
    public void refactorType(@RequestParam String oldType, @RequestParam String newType){
        foodTypeService.refactor(oldType, newType);
    }
}
