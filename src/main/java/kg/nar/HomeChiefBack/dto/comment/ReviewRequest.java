package kg.nar.HomeChiefBack.dto.comment;

import lombok.Data;

import java.util.UUID;

@Data
public class ReviewRequest {
    private int star;
    private String title;
    private UUID foodId;
}
