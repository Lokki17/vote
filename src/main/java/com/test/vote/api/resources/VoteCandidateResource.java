package com.test.vote.api.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.test.vote.api.resources.validation.VoteThemeExists;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * @author Lokki17
 * @since 29.08.2017
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@JsonInclude(value = NON_NULL)
public class VoteCandidateResource {

    private Long id;

    @NotEmpty
    private String name;

    private List<Long> votes;

    @VoteThemeExists
    private Long theme;
}
