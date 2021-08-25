package br.com.zupacademy.thiago.mercadolivre.validator;

import br.com.zupacademy.thiago.mercadolivre.transacao.StatusTransacao;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

@Component
public class TransacaoJaProcessadaValidator implements ConstraintValidator<TransacaoJaProcessada, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean isValid(Long idTransacaoGateway, ConstraintValidatorContext context) {


        if (idTransacaoGateway == null) {
            return true;
        }

        String jpql = "SELECT t FROM Transacao t WHERE t.idTransacaoGateway = :idTransacaoGateway";
        Query query = this.entityManager.createQuery(jpql);
        query.setParameter("idTransacaoGateway", idTransacaoGateway);

        List<Object> result = query.getResultList();

        return result.size() == 0 ? true : false;
    }
}