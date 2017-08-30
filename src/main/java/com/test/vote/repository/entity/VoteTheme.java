package com.test.vote.repository.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
public class VoteTheme extends AbstractEntity {

    @NotNull
    private String theme;

    @OneToMany
    private Set<VoteCandidate> voteCandidates;

    private LocalDateTime startVote;

    private LocalDateTime finishVote;
}
