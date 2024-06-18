package kg.nar.HomeChiefBack.dto.comment;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponse {

    private Long id;
    private String username;
    private LocalDateTime time;
    private String title;
}
