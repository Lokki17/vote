package com.test.vote.api.mappers;

import com.test.vote.api.resources.VoteThemeResource;
import com.test.vote.repository.entity.Vote;
import com.test.vote.repository.entity.VoteCandidate;
import com.test.vote.repository.entity.VoteTheme;
import com.test.vote.services.VoteCandidateService;
import com.test.vote.services.VoteThemeService;
import javaslang.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Lokki17
 * @since 29.08.2017
 */
@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class VoteThemeMapper {

    @NotNull
    private final VoteCandidateService voteCandidateService;

    public VoteTheme fromResource(VoteThemeResource source, VoteTheme destination) {
        destination.setTheme(source.getTheme());
        destination.setFinishVote(source.getFinishVote());
        destination.setStartVote(source.getStartVote());

        return destination;
    }

    public VoteThemeResource toResource(VoteTheme source) {
        Map<VoteCandidate, Integer> map = source.getVoteCandidates().stream()
                .collect(Collectors.groupingBy(Vote::getCandidate))

        return VoteThemeResource.builder()
                .id(source.getId())
                .build();
    }
}
