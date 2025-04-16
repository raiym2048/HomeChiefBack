package kg.nar.HomeChiefBack.controller;

import kg.nar.HomeChiefBack.dto.ObjectDto;
import kg.nar.HomeChiefBack.service.FoodTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    @DeleteMapping("/type/delete/{id}")
    public void deleteType(@PathVariable UUID id){
        foodTypeService.deleteType(id);
    }
    @PutMapping("/type/refactor/{id}")
    public void refactorType(@PathVariable UUID id, @RequestParam String newType){
        foodTypeService.refactor(id , newType);
    }
}
