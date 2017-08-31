package com.test.vote.api.useragent.impl;

import com.test.vote.api.useragent.UserAgent;
import com.test.vote.repository.entity.User;
import javaslang.control.Option;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Lokki17
 * @since 29.08.2017
 */
@AllArgsConstructor
@Builder
public class CurrentUser implements UserAgent {

    private final Authentication authentication;

    @Override
    public Option<User> currentUser() {
        return authentication()
                .map(Authentication::getPrincipal)
                .filter(principal -> principal instanceof User)
                .map(principal -> Option.of((User) principal))
                .getOrElse(Option.none());
    }

    @Override
    public Option<Authentication> authentication() {
        return Option.of(authentication);
    }


}
