package com.test.vote.api.mappers;

import com.test.vote.api.resources.UserResource;
import com.test.vote.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Lokki17
 * @since 29.08.2017
 */
@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class UserMapper {

    public User fromResource(UserResource source, User destination) {
        destination.setName(source.getName());
        destination.setEmail(source.getEmail());
        destination.setPassword(source.getPassword());
        return destination;
    }

    public UserResource toResource(User source) {
        return UserResource.builder()
                .id(source.getId())
                .name(source.getName())
                .email(source.getEmail())
                .build();
    }
}
