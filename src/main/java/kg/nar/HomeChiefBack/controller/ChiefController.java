package kg.nar.HomeChiefBack.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kg.nar.HomeChiefBack.dto.food.FoodAddRequest;
import kg.nar.HomeChiefBack.service.ChiefService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chief")
public class ChiefController {
    private final ChiefService chiefService;
    @PostMapping(value = "/food/add", consumes = "multipart/form-data")
    public ResponseEntity<?> addFood(
            @RequestHeader("Authorization") String token,
            @RequestPart("file") List<MultipartFile> files,
            @RequestPart("data") String foodDataString) throws JsonProcessingException {  // Note: Changed to String to debug
        ObjectMapper mapper = new ObjectMapper();
        FoodAddRequest foodAddRequest = mapper.readValue(foodDataString, FoodAddRequest.class);
        System.out.println(foodAddRequest.getName());  // Example of accessing the data

        chiefService.addFood(token,files, foodAddRequest);
        return ResponseEntity.ok("Food added");
    }
}
