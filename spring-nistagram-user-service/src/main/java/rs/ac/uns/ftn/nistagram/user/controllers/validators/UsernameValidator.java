package rs.ac.uns.ftn.nistagram.user.controllers.validators;


import rs.ac.uns.ftn.nistagram.user.controllers.constraints.UsernameConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class UsernameValidator implements ConstraintValidator<UsernameConstraint, String> {
    @Override
    public void initialize(UsernameConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (username == null)
            return false;
        return !isEmpty(username) && matchesPattern(username);
    }

    private boolean isEmpty(String username) {
        return username.length() == 0;
    }

    private boolean matchesPattern(String username) {
        return Pattern.compile("^(?=[a-zA-Z0-9._]{4,20}$)(?!.*[_.]{2})[^_.].*[^_.]$")
                .matcher(username).find();
    }


}
