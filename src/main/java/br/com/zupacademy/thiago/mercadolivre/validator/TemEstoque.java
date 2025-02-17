package br.com.zupacademy.thiago.mercadolivre.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = TemEstoqueValidator.class)
public @interface TemEstoque {

    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    String message() default "Não há estoque suficiente";
}
