package com.test.vote.api.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.test.vote.api.resources.validation.EmailDoestExists;
import lombok.*;

import javax.validation.constraints.NotNull;

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
public class UserResource {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String password;

    @NotNull
    @EmailDoestExists
    private String email;
}
