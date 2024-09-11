package kg.nar.HomeChiefBack.dto.comment;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class CommentResponse {

    private UUID id;
    private String username;
    private LocalDateTime time;
    private String title;
}
