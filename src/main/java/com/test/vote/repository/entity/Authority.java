package com.test.vote.repository.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Lokki17
 * @since 30.08.2017
 */
public enum  Authority implements GrantedAuthority {

    ROLE_USER,

    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
