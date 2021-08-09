package br.com.zupacademy.thiago.mercadolivre.validator;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail,String> {

    @PersistenceContext
    private EntityManager entityManager;

    private Class<?> modelo;
    private String campo;

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        this.modelo = constraintAnnotation.modelo();
        this.campo = constraintAnnotation.campo();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        StringBuilder jpql = new StringBuilder();
        jpql.append(" SELECT m FROM ");
        jpql.append(this.modelo.getSimpleName());
        jpql.append(" m WHERE ");
        jpql.append(this.campo);
        jpql.append(" = :value");

        Query query = this.entityManager.createQuery(jpql.toString());
        query.setParameter("value", value);

        List<Object> result = query.getResultList();

        return result.size() == 0 ? true : false;
    }
}
