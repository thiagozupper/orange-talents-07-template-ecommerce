package br.com.zupacademy.thiago.mercadolivre.validator;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

@Component
public class IdExistsValidator implements ConstraintValidator<IdExists, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    private Class<?> modelo;
    private String campo;

    @Override
    public void initialize(IdExists constraintAnnotation) {
        this.modelo = constraintAnnotation.modelo();
        this.campo = constraintAnnotation.campo();
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }

        StringBuilder jpql = new StringBuilder();
        jpql.append(" SELECT m FROM ");
        jpql.append(this.modelo.getSimpleName());
        jpql.append(" m WHERE ");
        jpql.append(this.campo);
        jpql.append(" = ");
        jpql.append(value);

        List<Object> result = this.entityManager.createQuery(jpql.toString()).getResultList();

        return result.size() == 0 ? false : true;
    }
}