package com.test.vote.repository.entity;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Lokki17
 * @since 29.08.2017
 */
@Table(name = "user_")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User extends AbstractEntity {

    @NotEmpty
    private String name;

    @NotEmpty
    private String password;

    @NotEmpty
    @Column(unique = true)
    private String email;
}
