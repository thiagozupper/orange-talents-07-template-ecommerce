package br.com.zupacademy.thiago.mercadolivre.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = IdExistsValidator.class)
public @interface IdExists {

    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

    String message() default "O id informado não existe";
    Class<?> modelo();
    String campo();

}