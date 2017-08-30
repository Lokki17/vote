package com.test.vote.repository.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
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
    private String name;

    @OneToMany
    private Set<VoteCandidate> voteCandidates = new HashSet<>();

    private LocalDateTime startVote;

    private LocalDateTime finishVote;
}
