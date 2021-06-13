package rs.ac.uns.ftn.nistagram.user.controllers.validators;


import rs.ac.uns.ftn.nistagram.user.controllers.constraints.FullnameConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class FullnameValidator implements ConstraintValidator<FullnameConstraint, String> {
    @Override
    public void initialize(FullnameConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String fullname, ConstraintValidatorContext constraintValidatorContext) {
        if (fullname == null)
            return false;
        return !isEmpty(fullname) && matchesPattern(fullname);
    }

    private boolean matchesPattern(String fullname) {
        return Pattern.compile("^[a-zA-Z]{4,}(?: [a-zA-Z]+){0,2}$").matcher(fullname).find();
    }

    private boolean isEmpty(String fullname) {
        return fullname.length() == 0;
    }

}
