package com.test.vote.api.converters;

import com.test.vote.repository.entity.VoteCandidate;
import com.test.vote.services.VoteCandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;


@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class IdToVoteCandidateConverter extends AbstractIdStringToObjectConverter<VoteCandidate> {

    @NotNull
    private final VoteCandidateService service;

    @Override
    public VoteCandidate convert(String id) {
        return convert(id, service::get);
    }
}
