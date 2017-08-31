package com.test.vote.api.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.test.vote.api.resources.validation.IsStartBeforeFinish;
import com.test.vote.api.resources.validation.IsTimeAfterNow;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;
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
public class VoteThemeResource extends ResourceSupport implements Serializable {

    private Long themeId;

    @NotEmpty
    private String name;

    @NotNull
    @IsTimeAfterNow
    private LocalDateTime startVote;

    @NotNull
    @IsTimeAfterNow
    private LocalDateTime finishVote;

    private Long[] voteCandidates;

    @Null
    private Map<Long, Integer> result;
}
