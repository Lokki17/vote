package com.test.vote.repository.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

/**
 * @author Lokki17
 * @since 29.08.2017
 */
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Vote extends AbstractEntity {

    @OneToOne
    @NotNull
    private User user;

    @OneToOne
    @NotNull
    private VoteCandidate candidate;
}
