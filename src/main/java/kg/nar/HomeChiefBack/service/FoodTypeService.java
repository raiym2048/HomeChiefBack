package kg.nar.HomeChiefBack.service;

import kg.nar.HomeChiefBack.dto.ObjectDto;

import java.util.List;
import java.util.UUID;

public interface FoodTypeService {
    List<ObjectDto> getTypes();

    void addType(String type);

    void deleteType(String type);

    void refactor(UUID uuid, String newType);
}
