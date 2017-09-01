package com.test.vote.api.resources.validation;

import com.test.vote.api.resources.VoteThemeResource;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Lokki
 * @since 26.01.2017
 */
@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = IsStartBeforeFinish.Validator.class)
public @interface IsStartBeforeFinish {

    String message() default "{FinishVote time is before StartVote}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<IsStartBeforeFinish, VoteThemeResource> {

        @Override
        public void initialize(IsStartBeforeFinish constraintAnnotation) {

        }

        @Override
        public boolean isValid(VoteThemeResource resource, ConstraintValidatorContext context) {
            if (resource.getStartVote() == null) {
                return true;
            }

            if (resource.getFinishVote() == null) {
                return true;
            }

            return resource.getStartVote().isBefore(resource.getFinishVote());
        }
    }
}
