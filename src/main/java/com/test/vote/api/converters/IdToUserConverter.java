package com.test.vote.api.converters;

import com.test.vote.repository.entity.User;
import com.test.vote.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;


@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class IdToUserConverter extends AbstractIdStringToObjectConverter<User> {

    @NotNull
    private final UserService service;

    @Override
    public User convert(String id) {
        return convert(id, service::get);
    }
}
