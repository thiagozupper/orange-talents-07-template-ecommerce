package br.com.zupacademy.thiago.mercadolivre.validator;

import br.com.zupacademy.thiago.mercadolivre.compra.Compra;
import br.com.zupacademy.thiago.mercadolivre.transacao.StatusTransacao;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

@Component
public class CompraFinalizadaComSucessoValidator implements ConstraintValidator<CompraFinalizadaComSucesso, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean isValid(Long compraId, ConstraintValidatorContext context) {

        if (compraId == null) {
            return true;
        }

        String jpql = "SELECT t FROM Transacao t WHERE t.compra.id = :compraId AND t.status = :status";
        Query query = this.entityManager.createQuery(jpql);
        query.setParameter("compraId", compraId);
        query.setParameter("status", StatusTransacao.SUCESSO.getCodigo());

        List<Object> result = query.getResultList();

        return result.size() == 0 ? true : false;
    }
}