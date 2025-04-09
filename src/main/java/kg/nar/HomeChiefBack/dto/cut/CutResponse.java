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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getStars() {
        return stars;
    }

    public void setStars(double stars) {
        this.stars = stars;
    }

    public UUID getChiefId() {
        return chiefId;
    }

    public void setChiefId(UUID chiefId) {
        this.chiefId = chiefId;
    }

    public String getChiefUsername() {
        return chiefUsername;
    }

    public void setChiefUsername(String chiefUsername) {
        this.chiefUsername = chiefUsername;
    }

    public List<UUID> getFoodIds() {
        return foodIds;
    }

    public void setFoodIds(List<UUID> foodIds) {
        this.foodIds = foodIds;
    }

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public int getLikedCount() {
        return likedCount;
    }

    public void setLikedCount(int likedCount) {
        this.likedCount = likedCount;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

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
