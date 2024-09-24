package kg.nar.HomeChiefBack.service.impl;

import kg.nar.HomeChiefBack.dto.comment.CommentResponse;
import kg.nar.HomeChiefBack.dto.cut.CutRequest;
import kg.nar.HomeChiefBack.dto.cut.CutResponse;
import kg.nar.HomeChiefBack.entity.*;
import kg.nar.HomeChiefBack.enums.Role;
import kg.nar.HomeChiefBack.exception.BadRequestException;
import kg.nar.HomeChiefBack.exception.NotFoundException;
import kg.nar.HomeChiefBack.mapper.CutMapper;
import kg.nar.HomeChiefBack.mapper.FoodMapper;
import kg.nar.HomeChiefBack.repository.CommentRepository;
import kg.nar.HomeChiefBack.repository.CutRepository;
import kg.nar.HomeChiefBack.repository.FoodRepository;
import kg.nar.HomeChiefBack.service.AuthService;
import kg.nar.HomeChiefBack.service.CutService;
import kg.nar.HomeChiefBack.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CutServiceImpl implements CutService {
    private final FileService fileService;
    private final FoodRepository foodRepository;
    private final AuthService authService;
    private final CommentRepository commentRepository;
    private final CutRepository cutRepository;
    private final FoodMapper foodMapper;
    private final CutMapper cutMapper;

    @Override
    public List<CutResponse> getAll(String token, PageRequest pageRequest) {
        User user = null;
        if (token != null && !token.isEmpty()) {
            user = authService.getUsernameFromToken(token);
        }

        return cutMapper.toDtoS(cutRepository.findAll(pageRequest).getContent(), user);
    }


    @Override
    public List<CommentResponse> getCutComments(UUID cutId) {
        Optional<Cut> cutOptional = cutRepository.findById(cutId);
        if (cutOptional.isEmpty())
            throw new NotFoundException("cut not found with id: "+ cutId, HttpStatus.NOT_FOUND);
        return foodMapper.commentToDtoS(cutOptional.get());
    }

    @Override
    public void comment(UUID cutId, String token, String title) {
        User user = authService.getUsernameFromToken(token);
        Optional<Cut> cut = cutRepository.findById(cutId);
        if (cut.isEmpty())
            throw new NotFoundException("not found cut with id: "+ cutId, HttpStatus.NOT_FOUND);

        Comments comment = new Comments();
        comment.setCut(cut.get());
        comment.setUser(user);
        comment.setTime(LocalDateTime.now());
        comment.setTitle(title);
        commentRepository.save(comment);

        cut.get().getComments().add(comment);

        cutRepository.save(cut.get());

    }

    @Override
    public void addCut(String authorization, MultipartFile file, CutRequest cutRequest) {
        Optional.ofNullable(authorization).orElseThrow(() -> new BadRequestException("Token is required"));
        Optional.ofNullable(cutRequest).orElseThrow(() -> new BadRequestException("FoodAddRequest is required"));
        User user = authService.getUsernameFromToken(authorization);
        if (user.getRole().equals(Role.CHIEF)) {
            Chief chief = user.getChief();
            createCut(chief, cutRequest, user.getId(), file);
        }
        else throw new BadRequestException("User is not a chief");
    }

    @Override
    public CutResponse getById(String token, UUID cutId) {
        User user = null;
        if (token != null && !token.isEmpty()) {
            user = authService.getUsernameFromToken(token);
        }
        Optional<Cut> cutOptional = cutRepository.findById(cutId);
        if (cutOptional.isEmpty())
            throw new NotFoundException("cut not found with id: "+ cutId, HttpStatus.NOT_FOUND);

        return cutMapper.toDto(cutOptional.get(), user);
    }

    private void createCut(Chief chief, CutRequest cutRequest, UUID userId, MultipartFile file) {
        Cut cut = new Cut();
        cut.setChief(chief);
        cut.setName(cutRequest.getName());
        cut.setDescription(cutRequest.getDescription());
        cut.setFood(getFoods(cutRequest.getFoodIds()));
        cut.setUrl(uploadFoodsImages(file, userId));
        cutRepository.save(cut);
    }

    private List<Food> getFoods(List<UUID> foodIds) {
        if (foodIds.size() > 3)
            throw new BadRequestException("Too many food ids");
        List<Food> foods = new ArrayList<>();
        for (UUID foodId : foodIds) {
            Optional<Food> foodOptional = foodRepository.findById(foodId);
            if (foodOptional.isEmpty())
                throw new NotFoundException("food not found with id: "+ foodId, HttpStatus.NOT_FOUND);
            foods.add(foodOptional.get());
        }
        return foods;
    }

    private String uploadFoodsImages(MultipartFile file, UUID userId) {
            try {
                return fileService.uploadFile(file, userId, 0);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

    }
}
