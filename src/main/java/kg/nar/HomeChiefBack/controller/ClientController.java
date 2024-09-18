package kg.nar.HomeChiefBack.controller;

import kg.nar.HomeChiefBack.dto.ObjectDto;
import kg.nar.HomeChiefBack.dto.bucket.BucketResponse;
import kg.nar.HomeChiefBack.dto.food.FoodResponse;
import kg.nar.HomeChiefBack.repository.CutRepository;
import kg.nar.HomeChiefBack.service.ClientService;
import kg.nar.HomeChiefBack.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;


import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @Value("${upload.dir}")
    private String uploadDir;

    @GetMapping("/file")
    public ResponseEntity<Resource> getFile(@RequestParam String name) throws IOException {
        Path filePath = Paths.get(uploadDir).resolve(name).normalize();
        Resource resource;

        try {
            resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                throw new RuntimeException("File not found: " + name);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error in file URL: " + name, e);
        }

        // Determine the file's content type
        String contentType = Files.probeContentType(filePath);

        // Default to binary stream if type is not determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        // Return the file as a download
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .contentType(MediaType.parseMediaType(contentType))  // Dynamically set the content type
                .body(resource);
    }

}
