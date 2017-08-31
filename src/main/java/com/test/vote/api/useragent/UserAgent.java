package com.test.vote.api.useragent;

import com.test.vote.repository.entity.User;
import javaslang.control.Option;
import org.springframework.security.core.Authentication;

/**
 * @author Lokki17
 * @since 29.08.2017
 */
public interface UserAgent {

    Option<User> currentUser();

    Option<Authentication> authentication();

}
