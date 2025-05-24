package kg.nar.HomeChiefBack.dto.food;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
public class FoodAddRequest {
    private String name;
    private String description;
    private int price;
    private int discount;
    private UUID foodTypeId;
}
