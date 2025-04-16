package kg.nar.HomeChiefBack.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import kg.nar.HomeChiefBack.dto.chief.AddressRequest;
import kg.nar.HomeChiefBack.dto.chief.ChiefInfoResponse;
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
    public void complete (@RequestBody AddressRequest addressRequest, HttpServletRequest request){
        chiefService.completeRegistration(addressRequest, request.getHeader("Authorization"));
    }


    @GetMapping("/info/{userId}")
    public Chief chiefInfo(@PathVariable UUID userId){
        return chiefService.chiefGetInfo(userId);
    }


    @GetMapping("/chiefs")
    List<ChiefInfoResponse> allChiefs() {
        return chiefService.allChiefs();
    }

}
