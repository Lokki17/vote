package com.test.vote.api.mappers;

import com.test.vote.api.resources.VoteThemeResource;
import com.test.vote.repository.entity.AbstractEntity;
import com.test.vote.repository.entity.VoteTheme;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Lokki17
 * @since 29.08.2017
 */
@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class VoteThemeMapper {

    @NotNull
    private final VoteCandidateMapper mapper;

    public VoteTheme fromResource(VoteThemeResource source, VoteTheme destination) {
        destination.setName(source.getName());
        destination.setFinishVote(source.getFinishVote());
        destination.setStartVote(source.getStartVote());

        return destination;
    }

    public VoteThemeResource toResource(VoteTheme source) {
        Map<Long, Integer> map = new HashMap<>();

        source.getVoteCandidates()
                .forEach(candidate -> map.put(candidate.getId(), candidate.getVotes().size()));

        return VoteThemeResource.builder()
                .id(source.getId())
                .name(source.getName())
                .startVote(source.getStartVote())
                .finishVote(source.getFinishVote())
                .voteCandidates(source.getVoteCandidates().stream()
                        .map(AbstractEntity::getId)
                        .toArray(Long[]::new))
                .result(map)
                .build();
    }
}
