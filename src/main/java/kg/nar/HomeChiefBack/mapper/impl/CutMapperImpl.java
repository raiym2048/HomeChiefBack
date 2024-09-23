package kg.nar.HomeChiefBack.mapper.impl;

import kg.nar.HomeChiefBack.dto.cut.CutResponse;
import kg.nar.HomeChiefBack.entity.Cut;
import kg.nar.HomeChiefBack.entity.User;
import kg.nar.HomeChiefBack.mapper.CutMapper;
import kg.nar.HomeChiefBack.repository.CutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CutMapperImpl implements CutMapper {
    private final CutRepository cutRepository;


    @Override
    public List<CutResponse> toDtoS(List<Cut> all, User user) {
        List<CutResponse> cutResponses = new ArrayList<>();
        for (Cut cut : all) {
            cutResponses.add(toDto(cut, user));
        }
        return cutResponses;
    }

    @Override
    public CutResponse toDto(Cut cut, User user) {
        CutResponse cutResponse = new CutResponse();
        cutResponse.setId(cut.getId());
        cutResponse.setName(cut.getName());
        cutResponse.setDescription(cut.getDescription());
        if (!cut.getUrl().isEmpty()) {
            cutResponse.setVideo(cut.getUrl());
        }
        cutResponse.setChiefUsername(cut.getChief().getFirstname());

        if (user!=null) {
            cutResponse.setLiked(cut.getLikedUsers().contains(user));
            cutResponse.setFavorite(user.getFavoriteCuts().contains(cut));
        }
        cutResponse.setLikedCount(cut.getLikedUsers().size());
        cutResponse.setCommentCount(cut.getComments().size());
        cutResponse.setFavoriteCount(cutRepository.countUsersWhoFavorited(cut.getId()));
        cutResponse.setViewCount(cut.getViews().size());

        return cutResponse;
    }
}
