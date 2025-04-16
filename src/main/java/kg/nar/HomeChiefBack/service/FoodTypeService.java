package kg.nar.HomeChiefBack.service;

import kg.nar.HomeChiefBack.dto.ObjectDto;

import java.util.List;
import java.util.UUID;

public interface FoodTypeService {
    List<ObjectDto> getTypes();

    void addType(String type);

    void deleteType(UUID uuid);

    void refactor(UUID uuid, String newType);
}
