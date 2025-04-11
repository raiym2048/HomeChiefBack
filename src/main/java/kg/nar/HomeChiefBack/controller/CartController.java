package kg.nar.HomeChiefBack.controller;

import jakarta.servlet.http.HttpServletRequest;
import kg.nar.HomeChiefBack.dto.bucket.BucketResponse;
import kg.nar.HomeChiefBack.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CartController {
    private final CartService cartService;

    @PostMapping("/cart/add")
    private void addFoodToBucket(HttpServletRequest request,
                                 @RequestParam UUID foodId, @RequestParam int count){
        cartService.addFoodToBucket(foodId, count, request.getHeader("Authorization"));

    }
    @GetMapping("/private/cart")
    private List<BucketResponse> getBucket(HttpServletRequest request){
        return cartService.getBucket(request.getHeader("Authorization"));
    }
}
