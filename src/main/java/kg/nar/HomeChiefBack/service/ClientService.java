package kg.nar.HomeChiefBack.service;

import kg.nar.HomeChiefBack.dto.bucket.BucketResponse;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ClientService {
    void addFoodToBucket(UUID foodId, int count, String token);

    List<BucketResponse> getBucket(String token, PageRequest pageRequest);

    Resource getFiles(String name) throws IOException;
}
