package kg.nar.HomeChiefBack.service.impl;

import kg.nar.HomeChiefBack.dto.ObjectDto;
import kg.nar.HomeChiefBack.entity.FoodType;
import kg.nar.HomeChiefBack.exception.BadRequestException;
import kg.nar.HomeChiefBack.mapper.FoodMapper;
import kg.nar.HomeChiefBack.repository.FoodTypeRepository;
import kg.nar.HomeChiefBack.service.FoodTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FoodTypeServiceImpl implements FoodTypeService {
    private final FoodTypeRepository foodTypeRepository;
    private final FoodMapper foodMapper;
    @Override
    public List<ObjectDto> getTypes() {
        return foodMapper.toDtoStype(foodTypeRepository.findAll());
    }
    @Override
    public void addType(String type) {
        Optional<FoodType> foodType = foodTypeRepository.findByName(type);
        if (foodType.isEmpty()){
            FoodType newFoodType = new FoodType();
            newFoodType.setName(type);
            foodTypeRepository.save(newFoodType);
        }

    }

    @Override
    public void deleteType(UUID uuid) {
        foodTypeRepository.deleteById(uuid);
    }
    @Override
    public void refactor(UUID uuid, String newType) {
        Optional<FoodType> foodType = foodTypeRepository.findById(uuid);
        if (foodType.isEmpty())
            throw new BadRequestException("такой тип не существует!: "+ uuid);
        foodType.get().setName(newType);
        foodTypeRepository.save(foodType.get());

    }

}
