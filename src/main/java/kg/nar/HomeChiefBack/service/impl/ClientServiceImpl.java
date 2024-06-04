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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final FoodRepository foodRepository;
    private final AuthService authService;
    private final ClientRepository clientRepository;
    private final BucketRepository bucketRepository;
    private final FoodMapper foodMapper;
    @Override
    public void addFoodToBucket(Long foodId, int count, String token) {
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

    private List<BucketResponse> getResponse(List<Bucket> buckets) {
        return buckets.stream().map(bucket -> {
            BucketResponse bucketResponse = new BucketResponse();
            bucketResponse.setCount(bucket.getCount());
            bucketResponse.setFood(foodMapper.toDto(bucket.getFood()));
            return bucketResponse;
        }).toList();
    }
}
