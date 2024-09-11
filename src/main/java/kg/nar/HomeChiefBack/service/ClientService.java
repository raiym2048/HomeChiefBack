package kg.nar.HomeChiefBack.service;

import kg.nar.HomeChiefBack.dto.bucket.BucketResponse;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ClientService {
    void addFoodToBucket(UUID foodId, int count, String token);

    List<BucketResponse> getBucket(String token);

    Resource getFiles(String name) throws IOException;
}
