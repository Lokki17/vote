package com.test.vote.api.converters;

import com.test.vote.repository.entity.VoteTheme;
import com.test.vote.services.VoteThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;


@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class IdToVoteThemeConverter extends AbstractIdStringToObjectConverter<VoteTheme> {

    @NotNull
    private final VoteThemeService service;

    @Override
    public VoteTheme convert(String id) {
        return convert(id, service::get);
    }
}
