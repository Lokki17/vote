package com.test.vote.api.resources.validation;

import com.test.vote.services.UserService;
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
@Constraint(validatedBy = EmailDoestExists.Validator.class)
public @interface EmailDoestExists {

    String message() default "{VoteTheme doesn't exists}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<EmailDoestExists, String> {

        @Autowired
        private UserService userService;

        @Override
        public void initialize(EmailDoestExists constraintAnnotation) {

        }

        @Override
        public boolean isValid(String email, ConstraintValidatorContext context) {
            if (Objects.isNull(email)) {
                return true;
            }

            return userService.getByEmail(email).isEmpty();
        }
    }
}
