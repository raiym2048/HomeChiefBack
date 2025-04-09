package kg.nar.HomeChiefBack.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class RequestHistoryResponse {
    private UUID chiefId;
    private String requestStatus;
    private String chiefName;
    private String comment;
    private LocalDateTime time;
    private String modifiedBy;
}
