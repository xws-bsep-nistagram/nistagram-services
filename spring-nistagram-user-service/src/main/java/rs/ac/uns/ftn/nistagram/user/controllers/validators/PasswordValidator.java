package rs.ac.uns.ftn.nistagram.user.controllers.validators;


import rs.ac.uns.ftn.nistagram.user.controllers.constraints.PasswordConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {

    @Override
    public void initialize(PasswordConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null)
            return false;

        return !tooShort(password) && containsLowerLetter(password) && containsUpperLetter(password) &&
                containsDigit(password) && containsSpecialCharacter(password);
    }

    private boolean tooShort(String password) {
        return password.length() < 10;
    }

    private boolean containsUpperLetter(String password) {
        return Pattern.compile("^(?=.*[A-Z])").matcher(password).find();
    }

    private boolean containsLowerLetter(String password) {
        return Pattern.compile("^(?=.*[a-z])").matcher(password).find();
    }

    private boolean containsDigit(String password) {
        return Pattern.compile("^(?=.*\\d)").matcher(password).find();
    }

    private boolean containsSpecialCharacter(String password) {
        return Pattern.compile("\\W+").matcher(password).find();
    }
}
