package rs.ac.uns.ftn.nistagram.user.controllers.constraints;


import rs.ac.uns.ftn.nistagram.user.controllers.validators.FullnameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FullnameValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FullnameConstraint {
    String message() default "Provided full name is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
