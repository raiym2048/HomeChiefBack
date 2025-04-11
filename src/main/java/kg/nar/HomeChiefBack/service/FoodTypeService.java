package kg.nar.HomeChiefBack.service;

import kg.nar.HomeChiefBack.dto.ObjectDto;

import java.util.List;

public interface FoodTypeService {
    List<ObjectDto> getTypes();

    void addType(String type);

    void deleteType(String type);

    void refactor(String oldType, String newType);
}
