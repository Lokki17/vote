package com.test.vote.services;

import com.test.vote.repository.entity.VoteCandidate;
import com.test.vote.repository.entity.VoteTheme;
import javaslang.control.Option;

import java.util.List;

/**
 * @author Lokki17
 * @since 29.08.2017
 */
public interface VoteThemeService {

    Option<VoteTheme> get(Long id);

    List<VoteTheme> findAll();

    VoteTheme update(VoteTheme entity);

    VoteTheme create(VoteTheme entity);

    void delete(VoteTheme user);

    VoteTheme addCandidate(VoteCandidate candidate);

}
