package com.test.vote.api.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.test.vote.api.resources.validation.CandidateExists;
import lombok.*;

import java.io.Serializable;

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
public class VoteResource implements Serializable {

    private Long id;

    @CandidateExists
    private Long candidate;
}
