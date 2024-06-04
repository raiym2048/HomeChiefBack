package kg.nar.HomeChiefBack.service;

import kg.nar.HomeChiefBack.dto.bucket.BucketResponse;

import java.util.List;

public interface ClientService {
    void addFoodToBucket(Long foodId, int count, String token);

    List<BucketResponse> getBucket(String token);
}
