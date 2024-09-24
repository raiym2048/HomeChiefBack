package kg.nar.HomeChiefBack.service;

import kg.nar.HomeChiefBack.dto.comment.CommentResponse;
import kg.nar.HomeChiefBack.dto.cut.CutRequest;
import kg.nar.HomeChiefBack.dto.cut.CutResponse;
import kg.nar.HomeChiefBack.dto.food.FoodAddRequest;
import kg.nar.HomeChiefBack.dto.food.FoodResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface CutService {
    List<CutResponse> getAll(String token, PageRequest pageRequest);

    List<CommentResponse> getCutComments(UUID cutId);

    void comment(UUID cutId, String token, String title);

    void addCut(String authorization, MultipartFile file, CutRequest cutRequest);

    CutResponse getById(String authorization, UUID cutId);
}
