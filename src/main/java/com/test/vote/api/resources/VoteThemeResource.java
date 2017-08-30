package com.test.vote.api.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.test.vote.api.resources.validation.IsStartBeforeFinish;
import com.test.vote.repository.entity.VoteCandidate;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
@IsStartBeforeFinish
public class VoteThemeResource {

    private Long id;

    @NotEmpty
    private String theme;

    @NotNull
    private LocalDateTime startVote;

    @NotNull
    private LocalDateTime finishVote;

    private Map<VoteCandidate, Integer> result;
}
