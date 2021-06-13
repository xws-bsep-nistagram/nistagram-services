package rs.ac.uns.ftn.nistagram.user.controllers.validators;


import rs.ac.uns.ftn.nistagram.user.controllers.constraints.EmailConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<EmailConstraint, String> {
    @Override
    public void initialize(EmailConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email == null)
            return false;
        return !isEmpty(email) && matchesPattern(email);
    }

    private boolean matchesPattern(String email) {
        return Pattern.compile("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$")
                .matcher(email).find();
    }

    private boolean isEmpty(String email) {
        return email.length() == 0;
    }
}
