package kg.nar.HomeChiefBack.dto.comment;

import lombok.Data;

@Data
public class ReviewRequest {
    private int star;
    private String title;
    private Long foodId;
}
