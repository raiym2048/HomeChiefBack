package kg.nar.HomeChiefBack.dto.food;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodResponse {
    private Long foodId;
    private String name;
    private String description;
    private int price;
    private int discount;
    private String image;
    private String foodType;
    private String chiefName;
}
