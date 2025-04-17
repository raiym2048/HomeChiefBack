package kg.nar.HomeChiefBack.dto.food;

import kg.nar.HomeChiefBack.dto.chief.ChiefInfoResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class FoodResponse {
    private UUID id;
    private String name;
    private String description;
    private int price;
    private ChiefInfoResponse chiefInfo;

    private int discount;
    private String image;
    private List<String> additionalImage;
    private String foodType;
    private Boolean liked = false;
    private Boolean favorite = false;
    private int likedCount;
    private int favoriteCount;
    private int viewCount;
    private int commentCount;
}
