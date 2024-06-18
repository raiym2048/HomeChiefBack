package kg.nar.HomeChiefBack.service;

import kg.nar.HomeChiefBack.dto.food.FoodAddRequest;
import kg.nar.HomeChiefBack.entity.Chief;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ChiefService {
    void addFood(String token, List<MultipartFile> files, FoodAddRequest foodAddRequest);

    Object getFiles(String token) throws IOException;

    Chief chiefGetInfo(UUID userId);
}
