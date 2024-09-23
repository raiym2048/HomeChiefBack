package kg.nar.HomeChiefBack.dto.cut;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CutResponse {
    private UUID id;
    private String video;
    private String name;
    private String description;
    private double stars;
    private UUID chiefId;
    private String chiefUsername;
    private List<UUID> foodIds;
    private Boolean liked = false;
    private Boolean favorite = false;
    private int likedCount;
    private int favoriteCount;
    private int viewCount;
    private int commentCount;


}
