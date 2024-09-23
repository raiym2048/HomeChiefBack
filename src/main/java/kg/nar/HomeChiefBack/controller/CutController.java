package kg.nar.HomeChiefBack.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import kg.nar.HomeChiefBack.dto.comment.CommentResponse;
import kg.nar.HomeChiefBack.dto.cut.CutRequest;
import kg.nar.HomeChiefBack.dto.cut.CutResponse;
import kg.nar.HomeChiefBack.dto.food.FoodAddRequest;
import kg.nar.HomeChiefBack.dto.food.FoodResponse;
import kg.nar.HomeChiefBack.service.CutService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cut")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CutController {

    private final CutService cutService;

    @PostMapping(value = "/add", consumes = "multipart/form-data")
    public ResponseEntity<?> addFood(
            HttpServletRequest request,
            @RequestPart("file") MultipartFile file,
            @RequestPart("data") String foodDataString) throws JsonProcessingException {
        long maxFileSize = 40 * 1024 * 1024; // 100 MB в байтах

        if (file.getSize() > maxFileSize) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot upload more than 40 mb");
        }
        ObjectMapper mapper = new ObjectMapper();
        CutRequest cutRequest = mapper.readValue(foodDataString, CutRequest.class);

        cutService.addCut(request.getHeader("Authorization"),file, cutRequest);
        return ResponseEntity.ok("Cut added");
    }

    @GetMapping("/all")
    private List<CutResponse> foodResponses(HttpServletRequest request, @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) {
        return cutService.getAll(request.getHeader("Authorization"), PageRequest.of(page, size));
    }



    @GetMapping("/review/{cutId}")
    public List<CommentResponse> commentResponses(@PathVariable UUID cutId){
        return cutService.getCutComments(cutId);
    }

    @PostMapping("/comment/{cutId}")
    public void comment(HttpServletRequest request, @PathVariable UUID cutId, @RequestParam String commentTitle){
        cutService.comment(cutId, request.getHeader("Authorization"), commentTitle);
    }
}
