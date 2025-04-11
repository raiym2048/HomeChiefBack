package kg.nar.HomeChiefBack.service.impl;

import kg.nar.HomeChiefBack.dto.bucket.BucketResponse;
import kg.nar.HomeChiefBack.entity.Bucket;
import kg.nar.HomeChiefBack.entity.Client;
import kg.nar.HomeChiefBack.entity.Food;
import kg.nar.HomeChiefBack.entity.User;
import kg.nar.HomeChiefBack.enums.Role;
import kg.nar.HomeChiefBack.exception.NotFoundException;
import kg.nar.HomeChiefBack.mapper.FoodMapper;
import kg.nar.HomeChiefBack.repository.BucketRepository;
import kg.nar.HomeChiefBack.repository.ClientRepository;
import kg.nar.HomeChiefBack.repository.FoodRepository;
import kg.nar.HomeChiefBack.service.AuthService;
import kg.nar.HomeChiefBack.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final AuthService authService;
    private final FoodRepository foodRepository;
    private final BucketRepository bucketRepository;
    private final FoodMapper foodMapper;

    @Override
    public void addFoodToBucket(UUID foodId, int count, String token) {
        User user = authService.getUsernameFromToken(token);
        if (!user.getRole().equals(Role.CLIENT)) {
            throw new RuntimeException("User is not client!");
        }
        Client client = user.getClient();
        Bucket clientBucket = client.getBucket();
        Food food = foodRepository.findById(foodId).orElseThrow(() -> new RuntimeException("Food not found!"));
        if (!clientBucket.getFoods().contains(food)) {
           // Bucket bucket = buckets.stream().filter(b -> b.getFood().getId().equals(foodId)).findFirst().get();
            //bucket.setCount(bucket.getCount() + count);
            //bucketRepository.save(bucket);
        } else {
            Bucket bucket = new Bucket();
            bucket.setClient(client);
           // bucket.setFood(food);
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
       // Bucket bucket = client.getBuckets();
       // return getResponse(bucket);
        return null;
    }

    @Override
    public void removeFoodFromCard(UUID foodId, String authorization) {
        Optional<Food> food = foodRepository.findById(foodId);

        if (food.isEmpty())
            throw new NotFoundException("food with id: "+foodId+" not found!", HttpStatus.NOT_FOUND);
        User user = authService.getUsernameFromToken(authorization);
        if (user.getRole().equals(Role.CLIENT)){
            Optional<Bucket> bucket = bucketRepository.findByClientId(user.getClient().getId());

          //  user.getClient().getBuckets().remove(food.get());

        }

    }


    private List<BucketResponse> getResponse(List<Bucket> buckets) {
        return buckets.stream().map(bucket -> {
            BucketResponse bucketResponse = new BucketResponse();
            bucketResponse.setCount(bucket.getCount());
          //  bucketResponse.setFood(foodMapper.toDto(bucket.getFood(), null));
            return bucketResponse;
        }).toList();
    }


}
