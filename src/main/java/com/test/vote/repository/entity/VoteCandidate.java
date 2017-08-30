package com.test.vote.repository.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "theme_id"}))
public class VoteCandidate extends AbstractEntity {

    @NotNull
    private String name;

    @OneToMany
    private List<Vote> votes = new ArrayList<>();

    @OneToOne
    private VoteTheme theme;
}
