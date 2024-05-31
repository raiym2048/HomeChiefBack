package kg.nar.HomeChiefBack.dto.food;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FoodResponse {
    private Long id;
    private String name;
    private String description;
    private int price;
    private int discount;
    private String image;
    private List<String> additionalImage;
    private String foodType;
    private String chiefName;
}
