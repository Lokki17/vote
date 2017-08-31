package com.test.vote.api.useragent.impl;

import com.test.vote.api.useragent.UserAgent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * @author Lokki17
 * @since 29.08.2017
 */
@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class UserAgentFactory {

    public UserAgent getInstance(final Authentication authentication) {
        return new CurrentUser(authentication);
    }
}
