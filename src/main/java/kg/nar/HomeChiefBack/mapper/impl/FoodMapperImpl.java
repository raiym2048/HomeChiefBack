package kg.nar.HomeChiefBack.mapper.impl;

import kg.nar.HomeChiefBack.dto.ObjectDto;
import kg.nar.HomeChiefBack.dto.comment.CommentResponse;
import kg.nar.HomeChiefBack.dto.food.FoodResponse;
import kg.nar.HomeChiefBack.entity.*;
import kg.nar.HomeChiefBack.mapper.FoodMapper;
import kg.nar.HomeChiefBack.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FoodMapperImpl implements FoodMapper{
    private final FoodRepository foodRepository;
    @Override
    public List<FoodResponse> toDtoS(List<Food> all, User user) {
        List<FoodResponse> foodResponses = new ArrayList<>();
        for (Food food : all) {
            foodResponses.add(toDto(food, user));
        }
        return foodResponses;
    }

    @Override
    public FoodResponse toDto(Food food, User user) {
        FoodResponse foodResponse = new FoodResponse();
        foodResponse.setId(food.getId());
        foodResponse.setName(food.getName());
        foodResponse.setDescription(food.getDescription());
        foodResponse.setPrice(food.getPrice());
        foodResponse.setFoodType(food.getFoodType().getName());
        if (!food.getImages().isEmpty()) {
            foodResponse.setImage(food.getImages().get(0));
            food.getImages().remove(0);
            foodResponse.setAdditionalImage(food.getImages());
        }
        foodResponse.setDiscount(food.getDiscount());
        foodResponse.setChiefName(food.getChief().getFirstname());

        if (user!=null) {
            foodResponse.setLiked(food.getLikedUsers().contains(user));
            foodResponse.setFavorite(user.getFavoriteFoods().contains(food));
        }
        foodResponse.setLikedCount(food.getLikedUsers().size());
        foodResponse.setCommentCount(food.getComments().size());
        foodResponse.setFavoriteCount(foodRepository.countUsersWhoFavorited(food.getId()));
        foodResponse.setViewCount(food.getViews().size());

        return foodResponse;
    }

    @Override
    public List<CommentResponse> commentToDtoS(Cut cut) {
        List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comments comments: cut.getComments()){
            commentResponses.add(commentToDto(comments));
        }
        return commentResponses;
    }

    @Override
    public List<ObjectDto> toDtoStype(List<FoodType> all) {
        List<ObjectDto> responses = new ArrayList<>();
        for (FoodType foodType: all){
            responses.add(toDtoType(foodType));
        }
        return responses;
    }

    private ObjectDto toDtoType(FoodType foodType) {
        ObjectDto objectDto = new ObjectDto();
        objectDto.setId(foodType.getId());
        objectDto.setName(foodType.getName());
        return objectDto;
    }

    private CommentResponse commentToDto(Comments comments) {
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setTime(comments.getTime());
        commentResponse.setTitle(comments.getTitle());
        commentResponse.setUsername(comments.getUser().getUsername());
        commentResponse.setId(comments.getId());
        return commentResponse;
    }
}
