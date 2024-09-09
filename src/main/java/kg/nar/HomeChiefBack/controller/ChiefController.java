package kg.nar.HomeChiefBack.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kg.nar.HomeChiefBack.dto.chief.AddressRequest;
import kg.nar.HomeChiefBack.dto.food.FoodAddRequest;
import kg.nar.HomeChiefBack.entity.Chief;
import kg.nar.HomeChiefBack.service.ChiefService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chief")
public class ChiefController {
    private final ChiefService chiefService;

    @PostMapping("/complete")
    public void complete (@RequestBody AddressRequest addressRequest, @RequestHeader("Authorization") String token){
        chiefService.completeRegistration(addressRequest, token);
    }
    @PostMapping(value = "/food/add", consumes = "multipart/form-data")
    public ResponseEntity<?> addFood(
            @RequestHeader("Authorization") String token,
            @RequestPart("file") List<MultipartFile> files,
            @RequestPart("data") String foodDataString) throws JsonProcessingException {  // Note: Changed to String to debug
        if (files.size() > 5) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot upload more than 5 files");
        }
        ObjectMapper mapper = new ObjectMapper();
        FoodAddRequest foodAddRequest = mapper.readValue(foodDataString, FoodAddRequest.class);
        System.out.println(foodAddRequest.getName());  // Example of accessing the data

        chiefService.addFood(token,files, foodAddRequest);
        return ResponseEntity.ok("Food added");
    }
    @GetMapping("/files")
    public ResponseEntity<?> getFiles(@RequestHeader("Authorization") String token) throws IOException {
        return ResponseEntity.ok(chiefService.getFiles(token));
    }

    @GetMapping("/info/{userId}")
    public Chief chiefInfo(@PathVariable UUID userId){
        return chiefService.chiefGetInfo(userId);
    }
}
