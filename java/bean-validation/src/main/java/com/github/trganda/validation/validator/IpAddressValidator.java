package com.github.trganda.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpAddressValidator implements ConstraintValidator<IpAddress, String> {
    @Override
    public void initialize(IpAddress constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile("^([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})$");
        Matcher matcher = pattern.matcher(value);

        try {
            if (!matcher.matches()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Invalid IP address: " + value).addConstraintViolation();
                return false;
            } else {
                for (int i = 1; i <= 4; i++) {
                    int octet = Integer.parseInt(matcher.group(i));
                    if (octet > 255) {
                        return false;
                    }
                }
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
