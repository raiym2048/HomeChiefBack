package kg.nar.HomeChiefBack.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import kg.nar.HomeChiefBack.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FileController {
    private final FileService fileService;


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
                .contentType(MediaType.parseMediaType(contentType))  // Dynamically set the content type
                .body(resource);
    }


    @PostMapping(value = "/set-profile-image", consumes = "multipart/form-data")
    public void setProfileImage(  @RequestPart("file") MultipartFile file, HttpServletRequest request){
        fileService.setImage(file, request.getHeader("Authorization"));
    }

}
