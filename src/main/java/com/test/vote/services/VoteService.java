package com.test.vote.services;

import com.test.vote.repository.entity.Vote;
import javaslang.control.Option;

import java.util.List;

/**
 * @author Lokki17
 * @since 29.08.2017
 */
public interface VoteService {

    Option<Vote> get(Long id);

    List<Vote> findAll();

    Vote create(Vote entity);

}
