package kg.nar.HomeChiefBack.dto.food;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FoodAddRequest {
    private String name;
    private String description;
    private int price;
    private int discount;
    private Long foodTypeId;
}
