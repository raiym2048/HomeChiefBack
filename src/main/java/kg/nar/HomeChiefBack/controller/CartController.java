/*
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

    @PostMapping("/add")
    private void addFoodToBucket(HttpServletRequest request,
                                 @RequestParam UUID foodId, @RequestParam int count){
        cartService.addFoodToBucket(foodId, count, request.getHeader("Authorization"));

    }
    @GetMapping("/all")
    private List<BucketResponse> getBucket(HttpServletRequest request){
        return cartService.getBucket(request.getHeader("Authorization"));
    }

    @DeleteMapping("/remove/{foodId}")
    public void removeFoodFromCard(@PathVariable UUID foodId, HttpServletRequest request){
        cartService.removeFoodFromCard(foodId, request.getHeader("Authorization"));
    }
}
*/
