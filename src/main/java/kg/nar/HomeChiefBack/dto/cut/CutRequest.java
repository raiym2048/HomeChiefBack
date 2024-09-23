package kg.nar.HomeChiefBack.dto.cut;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CutRequest {
    private String name;
    private String description;

    private List<UUID> foodIds = new ArrayList<>();

}
