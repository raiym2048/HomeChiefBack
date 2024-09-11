package kg.nar.HomeChiefBack.controller;

import kg.nar.HomeChiefBack.dto.bucket.BucketResponse;
import kg.nar.HomeChiefBack.dto.food.FoodResponse;
import kg.nar.HomeChiefBack.repository.CutRepository;
import kg.nar.HomeChiefBack.service.ClientService;
import kg.nar.HomeChiefBack.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;


import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/client")
public class ClientController {
    private final CutRepository postRepository;
    private final FoodService foodService;
    private final ClientService clientService;

    @GetMapping("/foods")
    private List<FoodResponse> foodResponses(@RequestHeader(required = false, name = "Authorization") String token) {
        return foodService.getAll(token);
    }

    @PostMapping("/cart/add")
    private void addFoodToBucket(@RequestHeader("Authorization") String token,
                                 @RequestParam UUID foodId, @RequestParam int count){
        clientService.addFoodToBucket(foodId, count, token);

    }

    @GetMapping("/private/cart")
    private List<BucketResponse> getBucket(@RequestHeader("Authorization") String token){
        return clientService.getBucket(token);
    }

    @GetMapping("/file")
    public ResponseEntity<Resource> getFile(@RequestParam String name) throws IOException {
        Resource image = clientService.getFiles(name);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + name + "\"");
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
        headers.add(HttpHeaders.PRAGMA, "no-cache");
        headers.add(HttpHeaders.EXPIRES, "0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(image.contentLength())
                .contentType(MediaType.IMAGE_PNG) // Ensure this matches the type of the image
                .body(image);
    }
}
