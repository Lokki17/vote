package com.test.vote.repository.entity;

import com.google.common.collect.ImmutableList;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Lokki17
 * @since 29.08.2017
 */
@Table(name = "user_")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User extends AbstractEntity implements UserDetails {

    @NotEmpty
    private String name;

    @NotEmpty
    private String password;

    @NotEmpty
    @Column(unique = true)
    private String email;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Authority authority;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return ImmutableList.of(authority);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
