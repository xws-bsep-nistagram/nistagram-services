package rs.ac.uns.ftn.nistagram.user.controllers.constraints;

import rs.ac.uns.ftn.nistagram.user.controllers.validators.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailConstraint {
    String message() default "Provided email is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
