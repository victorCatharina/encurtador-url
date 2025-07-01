package br.com.victorCatharina.encurtador_url.validation.annotation;

import br.com.victorCatharina.encurtador_url.validation.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface PasswordConstraint {
    String message() default "Invalid Password! Password must contain at least 8 characters, with at least one uppercase letter, one lowercase letter, one number, and one special character (from: #?!@$%^&*-)";

    Class<?>[] groups() default {}; // Validation groups

    Class<? extends Payload>[] payload() default {}; // Payload for custom validation scenarios
}
