package kg.nar.HomeChiefBack.dto.bucket;

import kg.nar.HomeChiefBack.dto.food.FoodResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BucketResponse {
    private FoodResponse food;
    private int count;
}
