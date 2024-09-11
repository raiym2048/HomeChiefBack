package kg.nar.HomeChiefBack.service.impl;

import kg.nar.HomeChiefBack.dto.bucket.BucketResponse;
import kg.nar.HomeChiefBack.entity.Bucket;
import kg.nar.HomeChiefBack.entity.Client;
import kg.nar.HomeChiefBack.entity.Food;
import kg.nar.HomeChiefBack.entity.User;
import kg.nar.HomeChiefBack.enums.Role;
import kg.nar.HomeChiefBack.mapper.FoodMapper;
import kg.nar.HomeChiefBack.repository.BucketRepository;
import kg.nar.HomeChiefBack.repository.ClientRepository;
import kg.nar.HomeChiefBack.repository.FoodRepository;
import kg.nar.HomeChiefBack.service.AuthService;
import kg.nar.HomeChiefBack.service.ClientService;
import kg.nar.HomeChiefBack.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final FoodRepository foodRepository;
    private final AuthService authService;
    private final ClientRepository clientRepository;
    private final BucketRepository bucketRepository;
    private final FoodMapper foodMapper;
    private final FileService fileService;
    @Override
    public void addFoodToBucket(UUID foodId, int count, String token) {
        User user = authService.getUsernameFromToken(token);
        if (!user.getRole().equals(Role.CLIENT)) {
            throw new RuntimeException("User is not client!");
        }
        Client client = user.getClient();
        List<Bucket> buckets = client.getBuckets();
        Food food = foodRepository.findById(foodId).orElseThrow(() -> new RuntimeException("Food not found!"));
        if (buckets.stream().anyMatch(bucket -> bucket.getFood().getId().equals(foodId))) {
            Bucket bucket = buckets.stream().filter(b -> b.getFood().getId().equals(foodId)).findFirst().get();
            bucket.setCount(bucket.getCount() + count);
            bucketRepository.save(bucket);
        } else {
            Bucket bucket = new Bucket();
            bucket.setClient(client);
            bucket.setFood(food);
            bucket.setCount(count);
            bucketRepository.save(bucket);
        }
    }

    @Override
    public List<BucketResponse> getBucket(String token) {
        User user = authService.getUsernameFromToken(token);
        if (!user.getRole().equals(Role.CLIENT)) {
            throw new RuntimeException("User is not client!");
        }
        Client client = user.getClient();
        List<Bucket> buckets = client.getBuckets();
        return getResponse(buckets);
    }

    @Override
    public Resource getFiles(String name) throws IOException {
         return fileService.downloadFile(name);
    }

    private List<BucketResponse> getResponse(List<Bucket> buckets) {
        return buckets.stream().map(bucket -> {
            BucketResponse bucketResponse = new BucketResponse();
            bucketResponse.setCount(bucket.getCount());
            bucketResponse.setFood(foodMapper.toDto(bucket.getFood(), null));
            return bucketResponse;
        }).toList();
    }
}
