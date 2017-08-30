package com.test.vote.api.converters;

import com.test.vote.repository.entity.Vote;
import com.test.vote.services.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;


@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class IdToVoteConverter extends AbstractIdStringToObjectConverter<Vote> {

    @NotNull
    private final VoteService service;

    @Override
    public Vote convert(String id) {
        return convert(id, service::get);
    }
}
