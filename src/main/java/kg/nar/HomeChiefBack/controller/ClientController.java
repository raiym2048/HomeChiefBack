package kg.nar.HomeChiefBack.controller;

import com.sun.net.httpserver.Request;
import jakarta.servlet.http.HttpServletRequest;
import kg.nar.HomeChiefBack.dto.ObjectDto;
import kg.nar.HomeChiefBack.dto.bucket.BucketResponse;
import kg.nar.HomeChiefBack.exception.NotFoundException;
import kg.nar.HomeChiefBack.service.ChiefService;
import kg.nar.HomeChiefBack.service.ClientService;
import kg.nar.HomeChiefBack.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;


import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/client")
public class ClientController {
    private final ClientService clientService;
    private final ChiefService chiefService;



    @PostMapping("/cart/add")
    private void addFoodToBucket(HttpServletRequest request,
                                 @RequestParam UUID foodId, @RequestParam int count){
        clientService.addFoodToBucket(foodId, count, request.getHeader("Authorization"));

    }

    @GetMapping("/private/cart")
    private List<BucketResponse> getBucket(HttpServletRequest request, @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size){
        return clientService.getBucket(request.getHeader("Authorization"), PageRequest.of(page, size));
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
                throw new NotFoundException("File not found: " + name, HttpStatus.NOT_FOUND);
            }
        } catch (MalformedURLException e) {
            throw new NotFoundException("Error in file URL: " + name, HttpStatus.NOT_FOUND);
        }

        // Determine the file's content type
        String contentType = Files.probeContentType(filePath);

        // Default to binary stream if type is not determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        // Return the file as a download
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))  // Dynamically set the content type
                .body(resource);
    }

    @PostMapping("/average")
    public ObjectDto average(@RequestParam UUID chiefId, @RequestParam int count){
        return chiefService.setAverage(chiefId, count);
    }

}
