package com.test.vote.api.mappers;

import com.test.vote.api.resources.VoteCandidateResource;
import com.test.vote.repository.entity.AbstractEntity;
import com.test.vote.repository.entity.VoteCandidate;
import com.test.vote.services.VoteThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lokki17
 * @since 29.08.2017
 */
@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class VoteCandidateMapper {

    @NotNull
    private final VoteThemeService voteThemeService;

    public VoteCandidate fromResource(VoteCandidateResource source, VoteCandidate destination) {
        destination.setId(source.getCandidateId());
        destination.setName(source.getName());

        if (source.getTheme() != null) {
            voteThemeService.get(source.getTheme())
                    .peek(destination::setTheme);
        }

        return destination;
    }

    public VoteCandidateResource toResource(VoteCandidate source) {
        return VoteCandidateResource.builder()
                .candidateId(source.getId())
                .name(source.getName())
                .theme(source.getTheme().getId())
                .votes(source.getVotes().stream()
                        .map(AbstractEntity::getId)
                        .collect(Collectors.toList()))
                .build();
    }

    public List<VoteCandidateResource> toResource(List<VoteCandidate> candidates) {
        return candidates.stream()
                .map(this::toResource)
                .collect(Collectors.toList());
    }
}
