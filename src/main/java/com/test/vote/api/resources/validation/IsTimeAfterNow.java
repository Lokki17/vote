package com.test.vote.api.resources.validation;

import com.test.vote.services.VoteThemeService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.time.LocalDateTime;
import java.util.Objects;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Lokki
 * @since 26.01.2017
 */
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = IsTimeAfterNow.Validator.class)
public @interface IsTimeAfterNow {

    String message() default "{Time is before now}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<IsTimeAfterNow, LocalDateTime> {

        @Override
        public void initialize(IsTimeAfterNow constraintAnnotation) {

        }

        @Override
        public boolean isValid(LocalDateTime time, ConstraintValidatorContext context) {
            if (Objects.isNull(time)) {
                return true;
            }

            return time.isAfter(LocalDateTime.now());
        }
    }
}
