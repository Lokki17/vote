package com.test.vote.api.mappers;

import com.test.vote.api.resources.VoteResource;
import com.test.vote.repository.entity.User;
import com.test.vote.repository.entity.Vote;
import com.test.vote.services.VoteCandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * @author Lokki17
 * @since 29.08.2017
 */
@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class VoteMapper {

    @NotNull
    private final VoteCandidateService voteCandidateService;

    public Vote fromResource(VoteResource source, Vote destination, User user) {
        destination.setUser(user);
        voteCandidateService.get(source.getId())
                .peek(destination::setCandidate);

        return destination;
    }

    public VoteResource toResource(Vote source) {
        return VoteResource.builder()
                .id(source.getId())
                .candidate(source.getCandidate().getId())
                .user(source.getUser().getId())
                .build();
    }
}
