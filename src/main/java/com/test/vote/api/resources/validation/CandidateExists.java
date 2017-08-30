package com.test.vote.api.resources.validation;

import com.test.vote.services.VoteCandidateService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Objects;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Lokki
 * @since 26.01.2017
 */
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = CandidateExists.Validator.class)
public @interface CandidateExists {

    String message() default "{VoteCandidate doesn't exists}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<CandidateExists, Long> {

        @Autowired
        private VoteCandidateService voteCandidateService;

        @Override
        public void initialize(CandidateExists constraintAnnotation) {

        }

        @Override
        public boolean isValid(Long id, ConstraintValidatorContext context) {
            if (Objects.isNull(id)) {
                return true;
            }

            return voteCandidateService.get(id).isDefined();
        }
    }
}
