package rs.ac.uns.ftn.nistagram.user.controllers.constraints;


import rs.ac.uns.ftn.nistagram.user.controllers.validators.UsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameConstraint {
    String message() default "Provided username is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
