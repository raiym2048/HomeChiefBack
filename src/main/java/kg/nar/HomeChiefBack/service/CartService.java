package kg.nar.HomeChiefBack.service;

import kg.nar.HomeChiefBack.dto.bucket.BucketResponse;

import java.util.List;
import java.util.UUID;

public interface CartService {
    void addFoodToBucket(UUID foodId, int count, String authorization);

    List<BucketResponse> getBucket(String authorization);
}
