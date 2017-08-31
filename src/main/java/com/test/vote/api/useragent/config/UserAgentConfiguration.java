package com.test.vote.api.useragent.config;

import com.test.vote.api.useragent.UserAgent;
import com.test.vote.api.useragent.impl.UserAgentFactory;
import org.springframework.context.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Lokki17
 * @since 29.08.2017
 */
@Configuration
public class UserAgentConfiguration {

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
    UserAgent userAgent(UserAgentFactory userAgentFactory) {
        return userAgentFactory.getInstance(SecurityContextHolder.getContext().getAuthentication());
    }
}
