package kg.nar.HomeChiefBack.service;

import kg.nar.HomeChiefBack.dto.food.FoodAddRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ChiefService {
    void addFood(String token, List<MultipartFile> files, FoodAddRequest foodAddRequest);
}
